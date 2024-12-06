package com.example.oztoticpacappturistica.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.oztoticpacappturistica.R
import com.example.oztoticpacappturistica.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.graphhopper.GHRequest
import com.graphhopper.GraphHopper
import com.graphhopper.ResponsePath
import com.graphhopper.config.Profile
import com.graphhopper.util.Parameters
import com.graphhopper.util.shapes.GHPoint
import databases.AppDatabase
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var db: AppDatabase
    private var userLocationMarker: Marker? = null

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        mapView = binding.mapOffline

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapOffline)
        mapView.setUseDataConnection(false)
        setupOfflineMap()
    }

    private fun setupOfflineMap(){
        val destinationDir = File(requireContext().filesDir, "Nogales_2024-10-16_205049")
        val tileSource = XYTileSource(
            "Nogales",
            0, 16,
            256,
            ".png",
            arrayOf(File("file:///android_asset/Nogales_2024-10-16_205049/OSMPublicTransport").absolutePath)
        )
        mapView.setTileSource(tileSource)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(15.0)
        mapView.controller.setCenter(GeoPoint(18.8194, -97.1432))

        val gpxFile = File(destinationDir, "waypoints.gpx")
        if (gpxFile.exists()){
            loadWaypointsFromGPX()
        }

    }

    private fun loadWaypointsFromGPX() {
        try {
            val inputStream: InputStream = requireContext().assets.open("waypoints.gpx")
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(InputStreamReader(inputStream))

            var eventType = parser.eventType
            var currentGeoPoint: GeoPoint? = null

            while (eventType != XmlPullParser.END_DOCUMENT){
                val tagName = parser.name

                when (eventType){
                    XmlPullParser.START_TAG ->{
                        if (tagName == "wpt") {
                            val lat = parser.getAttributeValue(null, "lat").toDouble()
                            val lon = parser.getAttributeValue(null, "lon").toDouble()
                            currentGeoPoint = GeoPoint(lat, lon)
                        }
                    }
                    XmlPullParser.END_TAG ->{
                        if (tagName == "wpt" && currentGeoPoint != null){
                            val marker = Marker(mapView)
                            marker.position = currentGeoPoint
                            marker.title = "Punto de Interes"
                            marker.setOnMarkerClickListener{ clickedMarker, _ ->
                                lifecycleScope.launch {
                                    showMarkerInfo(clickedMarker)
                                }
                                true
                            }
                            mapView.overlays.add(marker)
                            currentGeoPoint = null
                        }
                    }
                }
                eventType = parser.next()
            }
            mapView.invalidate()
        } catch (e: Exception){
            e.printStackTrace()
        }


    }

    private fun calcularRuta(userLocation: GeoPoint, destination: GeoPoint) {
        val graphFilePath = File(requireContext().getExternalFilesDir(null), "Nogales_2024-10-16_205049")

        if (!graphFilePath.exists()) {
            Toast.makeText(requireContext(), "El archivo de ruta no existe", Toast.LENGTH_SHORT).show()
            return
        }

        val graphHopper = GraphHopper().apply {
            setGraphHopperLocation(graphFilePath.absolutePath)
            setProfiles(
                Profile("car").apply {
                    vehicle = "car"
                    weighting = "fastest"
                    isTurnCosts = false
                }
            )
            importOrLoad()
        }

        val startPoint = GHPoint(userLocation.latitude, userLocation.longitude)
        val endPoint = GHPoint(destination.latitude, destination.longitude)

        val request = GHRequest(startPoint, endPoint).apply {
            algorithm = Parameters.Algorithms.DIJKSTRA
            setProfile("car")
            this.putHint(Parameters.CH.DISABLE, true)
        }

        val response = graphHopper.route(request)

        if (response.hasErrors()) {
            Toast.makeText(requireContext(), "Error al calcular la ruta: ${response.errors.joinToString()}", Toast.LENGTH_SHORT).show()
            return
        }

        val path: ResponsePath = response.best

        if (path.points.size() > 0) {
            val pathOverlay = Polyline(mapView)
            pathOverlay.outlinePaint.strokeWidth = 10f
            pathOverlay.outlinePaint.color = Color.BLUE
            pathOverlay.setPoints(path.points.map { GeoPoint(it.lat, it.lon) })


            mapView.overlayManager.add(pathOverlay)
            mapView.invalidate()
        } else {
            Toast.makeText(requireContext(), "No se pudo calcular la ruta", Toast.LENGTH_SHORT).show()
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun getUserLocation(): Location? {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val locationRequest = LocationRequest.Builder(1000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxUpdates(1)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let{
                    val location = locationResult.lastLocation
                    if (location != null){
                        val latitude = location.latitude
                        val longitude = location.longitude

                        val geoPoint = GeoPoint(latitude, longitude)
                        showUserLocation(geoPoint)
                    }
                }
            }
        }

        if(ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        val locationTask: Task<Location> = fusedLocationClient.lastLocation
        locationTask.addOnSuccessListener { location ->
            location?.let {
                val latitude = location.latitude
                val longitude = location.longitude
                val geoPoint = GeoPoint(latitude, longitude)
                showUserLocation(geoPoint)
            }
        }
        return null
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showUserLocation(geoPoint: GeoPoint){
        val mapController = mapView.controller

        mapController.setCenter(geoPoint)
        mapController.setZoom(15.0)
        if(userLocationMarker == null){
            userLocationMarker = Marker(mapView).apply {
                position = geoPoint
                icon = resources.getDrawable(R.drawable.baseline_location_on_24)
                title = "Tu Ubicacion"
            }
            mapView.overlays.add(userLocationMarker)
        }else{
            userLocationMarker!!.position = geoPoint
        }
    }


    private suspend fun showMarkerInfo(marker: Marker) {
        val sitioId = marker.relatedObject as? Int
        sitioId?.let{ id ->
            val sitio = db.sitioDao().getSitioById(id)

            sitio?.let {
                AlertDialog.Builder(requireContext())
                    .setTitle(it.nombre)
                    .setMessage(it.informacion)
                    .setPositiveButton("Ver detalles") { _, _ ->
                        navigateToDetalleSitioFragment(it.id)
                    }
                    .setNegativeButton("Mostrar Ruta") { _, _ ->
                        val userLocation = getUserLocation()
                        if (userLocation != null){
                            val startPoint = GeoPoint(userLocation.latitude, userLocation.longitude)
                            val destination = GeoPoint(it.latitude, it.longitude)
                            calcularRuta(startPoint, destination)
                        }else{
                            Toast.makeText(requireContext(), "No se pudo obtener tu ubicación", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNeutralButton("Cerrar", null)
                    .setCancelable(true)
                    .create()
                    .show()
            }
        } ?: run{
            Toast.makeText(requireContext(), "Sitio no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToDetalleSitioFragment(sitioId: Int){
        val action = HomeFragmentDirections.actionNavHomeToDetalleSitioFragment(sitioId)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mapView.onDetach()
    }
}