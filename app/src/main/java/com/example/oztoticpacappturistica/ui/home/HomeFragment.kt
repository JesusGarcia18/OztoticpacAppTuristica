package com.example.oztoticpacappturistica.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.oztoticpacappturistica.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.graphhopper.GHRequest
import com.graphhopper.GraphHopper
import com.graphhopper.ResponsePath
import com.graphhopper.util.Parameters
import com.graphhopper.util.shapes.GHPoint
import org.osmdroid.views.overlay.Polyline

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private val destinationDir = File(requireContext().getExternalFilesDir(null), "assets/Nogales_2024-10-16_205049")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val action = HomeFragmentDirections.actionHomeFragmentToDetalleSitioFragment(sitioId)
        findNavController().navigate(action)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapOffline)
        mapView.setUseDataConnection(false)
        setupOfflineMap()
    }

    private fun setupOfflineMap(){
        if (!destinationDir.exists()){
            destinationDir.mkdirs()
        }

        val mapZipFileName = "Nogales_2024-10-16_205049.zip"
        val zipFileExists = File(destinationDir, mapZipFileName).exists()

        if (!zipFileExists){
            GlobalScope.launch(Dispatchers.Main){
                withContext(Dispatchers.IO){
                    extractZipAsset(mapZipFileName, destinationDir)
                }
            }
        }

        setupMap(destinationDir)
    }

    private suspend fun extractZipAsset(assetName: String, destinationDir: File){
        val assetManager = assets
        val zipFile = assetManager.open(assetName)
        val zipInputStream = ZipInputStream(zipFile)

        var zipEntry: ZipEntry?

        while(zipInputStream.nextEntry.also { zipEntry = it } != null){
            val newFile = File(destinationDir, zipEntry!!.name)
            if(zipEntry!!.isDirectory){
                newFile.mkdirs()
            }else{
                val outputStream: OutputStream = newFile.outputStream()
                val buffer = ByteArray(1024)
                var length: Int
                while (withContext(Dispatchers.IO) {
                        zipInputStream.read(buffer)
                    }.also { length = it } != -1){
                    withContext(Dispatchers.IO) {
                        outputStream.write(buffer, 0, length)
                    }
                }
                withContext(Dispatchers.IO) {
                    outputStream.close()
                }
            }
            withContext(Dispatchers.IO) {
                zipInputStream.closeEntry()
            }
        }
        withContext(Dispatchers.IO) {
            zipInputStream.close()
        }
    }

    private fun setupMap(){
        val tileSource = XYTileSource(
            "Nogales",
            0, 16,
            256,
            ".png",
            arrayOf(destinationDir.absolutePath)
        )
        mapView.setTileSource(tileSource)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(15.0)
        mapView.controller.setCenter(GeoPoint(18.8206, -97.1645))

        val gpxFile = File(destinationDir, "waypoints.gpx")
        if (gpxFile.exists()){
            loadWaypointsFromGPX(destinationDir)
        }
    }

    private fun loadWaypointsFromGPX() {
        try {
            val inputStream: InputStream = assets.open("waypoints.gpx")
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

                                val userLocation = getUserLocation()
                                val userLatitude = userLocation.latitude
                                val userLongitude = userLocation.longitude
                                val userPoint = GeoPoint(userLatitude, userLongitude)

                                val sitePoint = marker.position

                                calcularRuta(userPoint, sitePoint)
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
        val graphFilePath = File(requireContext().getExternalFilesDir(null), "assets/Nogales_2024-10-16_205049.zip")

        if (!graphFilePath.exists()) {
            Toast.makeText(requireContext(), "El archivo de ruta no existe", Toast.LENGTH_SHORT).show()
            return
        }

        val graphHopper = GraphHopper().apply {
            setGraphHopperLocation(graphFilePath.absolutePath)
            encodingManager = EncodingManager.create("car") // Configura el perfil, ejemplo: "car"
            importOrLoad()
        }

        val startPoint = GHPoint(userLocation.latitude, userLocation.longitude)
        val endPoint = GHPoint(destination.latitude, destination.longitude)

        val request = GHRequest(startPoint, endPoint).apply {
            algorithm = Parameters.Algorithms.DIJKSTRA
            hints = HintsMap().apply {
                put(Parameters.CH.DISABLE, true) // Desactiva la jerarquía contratada si no está precargada
            }
        }

        val response = graphHopper.route(request)

        if (response.hasErrors()) {
            Toast.makeText(requireContext(), "Error al calcular la ruta: ${response.errors.joinToString()}", Toast.LENGTH_SHORT).show()
            return
        }

        val path: ResponsePath = response.best

        if (path.points.size() > 0) {
            val polyline = Polyline().apply {
                setPoints(path.points.map { GeoPoint(it.lat, it.lon) })
                color = Color.BLUE
                width = 10f
            }

            mapView.overlays.add(polyline)
            mapView.invalidate()
        } else {
            Toast.makeText(requireContext(), "No se pudo calcular la ruta", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getUserLocation(callback: (Location?) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                callback(location)
            }
        }

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            callback(location)
        }
    }


    private suspend fun showMarkerInfo(marker: Marker) {
        val sitioId = marker.relatedObject as? Int
        val sitio = sitioId?.let { db.sitioDao().getSitioById(it) }

        sitio?.let {
            AlertDialog.Builder(requireContext())
                .setTitle(it.nombre)
                .setMessage(it.informacion)
                .setPositiveButton("Ver detalles") { _, _ ->
                    val action = HomeFragmentDirections.actionHomeFragmentToDetalleSitioFragment(it.id)
                    findNavController().navigate(action)
                }
                .setNegativeButton("Mostrar Ruta") { _, _ ->
                    val userLocation = getUserLocation()
                    val startPoint = GeoPoint(userLocation.latitude, userLocation.longitude)
                    val destination = GeoPoint(it.latitude, it.longitude)
                    calcularRuta(startPoint, destination)
                }
                .setNeutralButton("Cerrar", null)
                .setCancelable(true)
                .create()
                .show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mapView.onDetach()
    }
}