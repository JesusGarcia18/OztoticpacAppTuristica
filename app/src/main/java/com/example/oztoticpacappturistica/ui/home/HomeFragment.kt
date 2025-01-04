package com.example.oztoticpacappturistica.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.oztoticpacappturistica.MainActivity
import com.example.oztoticpacappturistica.R
import com.example.oztoticpacappturistica.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import databases.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.MapTileProviderArray
import org.osmdroid.tileprovider.modules.MBTilesFileArchive
import org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.io.File
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.overlay.infowindow.InfoWindow
import java.io.FileOutputStream
import java.net.URL
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.tan

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var db: AppDatabase
    private var userLocationMarker: Marker? = null
    private lateinit var nogalesBounds: BoundingBox

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    data class Waypoint( val geoPoint: GeoPoint, val name: String)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val applicationId = requireContext().packageName
        val osmDroidConfig = Configuration.getInstance()
        osmDroidConfig.userAgentValue = applicationId
        osmDroidConfig.osmdroidBasePath = File(requireContext().cacheDir, "osmdroid")
        osmDroidConfig.osmdroidTileCache = File(osmDroidConfig.osmdroidBasePath, "tile")

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val latitude = 18.8207762
        val longitude = -97.1642322
        val geoPoint = GeoPoint(latitude, longitude)


        mapView = view.findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.controller.setCenter(geoPoint)
        mapView.controller.setZoom(15.0)

        nogalesBounds = BoundingBox(18.979026, -97.119141, 18.729502, -97.294922)

        getUserLocation { currentLocation ->
            if (currentLocation != null) {
                showUserLocation(currentLocation)
            } else {
                val lastLocation = getLastLocationOffline()
                if (lastLocation != null) {
                    showUserLocation(lastLocation)
                } else {
                    Toast.makeText(requireContext(), "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
        }

        db = Room.databaseBuilder(
            requireContext(), AppDatabase::class.java, "sitios-database"
        ).build()

        configurarToolbar()

        mapOpen()

        addWaypointsToMap()

        val buttonLimpiarRuta: Button = view.findViewById(R.id.buttonLimpiarRuta)
        buttonLimpiarRuta.setOnClickListener {
            limpiarRuta()
        }
    }

    private fun configurarToolbar(){
        (requireActivity() as MainActivity).updateToolbar("¿Qué desea visitar hoy?", 0)
    }

    override fun onResume() {
        super.onResume()
        if (!isOfflineMapDownloaded()){
            downloadOfflineMap(nogalesBounds)
                val sharedPrefs = requireContext().getSharedPreferences("app_prefs",Context.MODE_PRIVATE)
                with (sharedPrefs.edit()){
                    putBoolean("offline_map_downloaded", true)
                    apply()
                }
            }
        getUserLocation { currentLocation ->
            if (currentLocation != null) {
                showUserLocation(currentLocation)
            } else {
                val lastLocation = getLastLocationOffline()
                if (lastLocation != null) {
                    showUserLocation(lastLocation)
                } else {
                    Toast.makeText(requireContext(), "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                downloadOfflineMap(nogalesBounds)
                Toast.makeText(requireContext(), "Permiso aceptado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Descarga de Mapas"
            val descriptionText = "Proceso de descarga del mapa de Nogales"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("offline_map_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun downloadOfflineMap(bounds: BoundingBox) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_PERMISSION
            )
        } else {
            return
        }
        createNotificationChannel()

        val notificationManager = NotificationManagerCompat.from(requireContext())
        val builder = NotificationCompat.Builder(requireContext(), "offline_map_channel")
            .setContentTitle("Descargando Mapa de Nogales")
            .setContentText("Progreso de la descarga")
            .setSmallIcon(R.drawable.baseline_downloading_24)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(100, 0, false)

        notificationManager.notify(1, builder.build())

        val destinationDir = File(requireContext().filesDir, "Nogales_Tiles")
        destinationDir.mkdirs()

        val tileSource = TileSourceFactory.MAPNIK

        val minZoom = 10
        val maxZoom = 16

        var totalTiles = 0
        var downloadedTiles = 0

        for (zoomLevel in minZoom..maxZoom){
            val upperLeftTileX = longToTileX(bounds.lonWest, zoomLevel)
            val upperLeftTileY = latToTileY(bounds.latNorth, zoomLevel)
            val lowerRightTileX = longToTileX(bounds.lonEast, zoomLevel)
            val lowerRightTileY = latToTileY(bounds.latSouth, zoomLevel)

            totalTiles += (lowerRightTileX - upperLeftTileX + 1) * (lowerRightTileY - upperLeftTileY + 1)
        }

        for (zoomLevel in minZoom..maxZoom) {
            val upperLeftTileX = longToTileX(bounds.lonWest, zoomLevel)
            val upperLeftTileY = latToTileY(bounds.latNorth, zoomLevel)
            val lowerRightTileX = longToTileX(bounds.lonEast, zoomLevel)
            val lowerRightTileY = latToTileY(bounds.latSouth, zoomLevel)

            for (x in upperLeftTileX..lowerRightTileX){
                for (y in upperLeftTileY..lowerRightTileY){
                    val tileUrl = tileSource.getTileURLString(MapTileIndex.getTileIndex(zoomLevel, x, y))
                    val tileFile = File(destinationDir, "$zoomLevel/$x/$y.png")
                    tileFile.parentFile?.mkdirs()

                    try{
                        val tileBytes = downloadTile(tileUrl)
                        saveTile(tileBytes, tileFile)
                        downloadedTiles++
                        val progress = (downloadedTiles * 100) / totalTiles
                        builder.setProgress(100, progress, false)
                        notificationManager.notify(1, builder.build())
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
        builder.setContentText("Descarga completada")
            .setProgress(100, 100, false)
        notificationManager.notify(1, builder.build())
    }

    private fun downloadTile(tileUrl: String): ByteArray{
        val url = URL(tileUrl)
        val connection = url.openConnection()
        connection.connect()

        return connection.getInputStream().use { it.readBytes() }
    }

    private fun saveTile(tileBytes: ByteArray, tileFile: File){
        try{
            tileFile.outputStream().use { outputStream ->
                outputStream.write(tileBytes)
            }
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun longToTileX(lon: Double, zoom: Int): Int {
        return ((lon + 180) / 360 * (1 shl zoom)).toInt()
    }
    private fun latToTileY(lat: Double, zoom: Int): Int {
        val radLat = Math.toRadians(lat)
        return ((1.0 - ln(tan(radLat) + 1 / cos(radLat)) / Math.PI) / 2 * (1 shl zoom)).toInt()
    }

    private fun isOfflineMapDownloaded(): Boolean {
        val sharedPrefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return  sharedPrefs.getBoolean("offline_map_downloaded", false)
    }

    private fun copyMBTilesToInternalStorage(context: Context): File {
        val fileName = "Nogales_Tiles.mbtiles"
        val inputStream = context.assets.open(fileName)
        val outputFile = File(context.filesDir, fileName)

        if (!outputFile.exists()) {
            inputStream.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }
        }

        return outputFile
    }

    private fun mapOpen(){
        val mbTilesFile = copyMBTilesToInternalStorage(requireContext())

        // Configurar el mapa con el archivo MBTiles
        val tileSource = XYTileSource("MBTiles", 14, 16, 256, ".png", arrayOf())
        val archiveFile = MBTilesFileArchive.getDatabaseFileArchive(File(mbTilesFile.absolutePath))
        val provider = MapTileFileArchiveProvider(
            SimpleRegisterReceiver(requireContext()),
            tileSource,
            arrayOf(archiveFile) // Archivo MBTiles convertido a IArchiveFile
        )
        val tileProvider = MapTileProviderArray(tileSource, null, arrayOf(provider))

        mapView.setTileSource(tileSource)
        mapView.tileProvider = tileProvider
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(15.0) // Zoom inicial
        mapView.controller.setCenter(GeoPoint(18.8207762, -97.1642322))
    }

    private val waypoints = arrayListOf(
        Waypoint( GeoPoint(18.8207762, -97.1642322), "Laguna de Nogales"),
        Waypoint( GeoPoint(18.820392,-97.165617 ), "Sitio Turistico de Relajacion OZTOTIPAC"),
        Waypoint( GeoPoint(18.8228142, -97.1701481), "Sendero Cerro de la Capilla"),
        Waypoint( GeoPoint(18.8326624, -97.1752187), "Cañon de la Carbonera"),
        Waypoint( GeoPoint(18.8388139, -97.1943655), "Mirador y Sendero \"Palo Verde\""),
        Waypoint( GeoPoint(18.8521762, -97.1954041), "Mirador \"Piedra del Aguila\""),
        Waypoint( GeoPoint(18.8538211, -97.196359), "Sendero al Balcon del Diablo"),
        Waypoint( GeoPoint(18.8532017, -97.1963482), "Tuneles Ferreos de la Carbonera (Rosecranz)"),
        Waypoint(GeoPoint(18.831615, -97.1656136), "Inicio del Sendero a Cueva de Marmol"),
        Waypoint(GeoPoint(18.8184128, -97.1963339), "Ojito de Agua \"El Encinar\""),
        Waypoint(GeoPoint(18.7933817, -97.1985023), "Rincon de las Doncellas"),
        Waypoint(GeoPoint(18.7874703, -97.1913998), "Cueva del Diablo"),
        Waypoint(GeoPoint(18.7764966, -97.2049903), "Taza de Agua"),
        Waypoint(GeoPoint(18.7891935, -97.1971413), "Paseo de los Ahuehuetes"),
        Waypoint(GeoPoint(18.7888858, -97.1973968), "Cañon del Rio Blanco"),
        Waypoint(GeoPoint(18.8128792, -97.1953152), "Parroquia de San Isidro Labrador"),
        Waypoint(GeoPoint(18.8220339, -97.1630438), "Parroquia de San Juan Bautista"),
        Waypoint(GeoPoint(18.815906, -97.1642025), "Ex Fabrica \"San Lorenzo\""),
        Waypoint(GeoPoint(18.8194235, -97.2303424), "Antigua Ruta del Ferrocarril"),
        Waypoint(GeoPoint(18.821645, -97.1626499), "Los Portales de Nogales"),
        Waypoint(GeoPoint(18.821760, -97.162661), "La Pizza"),
        Waypoint(GeoPoint(18.818914, -97.1619264), "Restaurante Xipatlani"),
        Waypoint(GeoPoint(18.7872872, -97.1991455), "Campestre Yecapixtla"),
        Waypoint(GeoPoint(18.8547765, -97.2123366), "Restaurante Chicahuaxtla"),
        Waypoint(GeoPoint(18.7887928, -97.1977154), "Balneario Relax"),
        Waypoint(GeoPoint(18.787291, -97.199259), "Balneario El Eden"),
        Waypoint(GeoPoint(18.8219735, -97.1589975), "Campo Deportivo \"Famosa\""),
        Waypoint(GeoPoint(18.8546202, -97.2128201), "Cabañas Chicahuaxtla \"Bosque Encantado\""),
        Waypoint(GeoPoint(18.8215511, -97.1626738), "Laguna Real de Nogales"),
        Waypoint(GeoPoint(18.8254306, -97.1611987), "Hotel Boutique \"El Mesón de Samaria\""),
        Waypoint(GeoPoint(18.8193632, -97.1646003), "Posada \"Las Garzas\"")
    )

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addWaypointsToMap() {
        for (waypoint in waypoints) {
            val marker = Marker(mapView)
            marker.icon = resources.getDrawable(R.drawable.baseline_place_24)
            marker.position = waypoint.geoPoint
            marker.title = waypoint.name
            marker.relatedObject = waypoint.name
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapView.overlays.add(marker)
            marker.setOnMarkerClickListener { clickedMarker, _ ->
                lifecycleScope.launch {
                    showMarkerInfo(clickedMarker)
                }
                true
            }
        }
        mapView.invalidate()
    }

    private fun getUserLocation(onLocationReceived: (GeoPoint?) -> Unit){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

            val locationRequest = LocationRequest.Builder(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxUpdates(1)
                .build()


            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                        val location = locationResult.lastLocation
                        if (location != null) {

                            val geoPoint = GeoPoint(location.latitude, location.longitude)
                            onLocationReceived(geoPoint)

                            val sharedPreferences = requireContext().getSharedPreferences(
                                "userLocation",
                                Context.MODE_PRIVATE
                            )
                            with(sharedPreferences.edit()){
                                putFloat("last_latitude", location.latitude.toFloat())
                                putFloat("last_longitude", location.longitude.toFloat())
                                apply()
                            }
                        } else {
                            onLocationReceived(null)
                        }

                }
            }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val geoPoint = GeoPoint(location.latitude, location.longitude)
                    showUserLocation(geoPoint)

                    val sharedPreferences =
                        requireContext().getSharedPreferences("userLocation", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()){
                        putFloat("last_latitude", location.latitude.toFloat())
                        putFloat("last_longitude", location.longitude.toFloat())
                        apply()
                    }
                } else {
                    onLocationReceived(null)
                }
            }.addOnFailureListener {
                onLocationReceived(null)
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            onLocationReceived(null)
        }
    }

    private fun getLastLocationOffline(): GeoPoint?{
        val sharedPreferences = requireContext().getSharedPreferences("userLocation", Context.MODE_PRIVATE)
        val latitude = sharedPreferences.getFloat("last_latitude", 0f)
        val longitude = sharedPreferences.getFloat("last_longitude", 0f)

        if (latitude != 0f && longitude != 0f){
            return GeoPoint(latitude.toDouble(), longitude.toDouble())
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

    inner class CustomInfoWindow( mapView: MapView) : InfoWindow(R.layout.info_window, mapView) {
        override fun onOpen(item: Any?) {
        }
        override fun onClose() {
        }
    }



    private suspend fun showMarkerInfo(marker: Marker) {
        val sitioNombre = marker.relatedObject as? String
        sitioNombre?.let { nombre ->
            val sitio = withContext(Dispatchers.IO) { db.sitioDao().getSitioByName(nombre) }

            sitio?.let {
                val infoWindow = CustomInfoWindow(mapView)

                infoWindow.view.findViewById<TextView>(R.id.title).text = it.nombre
                infoWindow.view.findViewById<TextView>(R.id.description).text = it.informacion

                infoWindow.view.findViewById<Button>(R.id.btnDetalles).setOnClickListener {
                    navigateToDetalleSitioFragment(sitio.nombre)
                    infoWindow.close()
                }
                infoWindow.view.findViewById<Button>(R.id.btnRuta).setOnClickListener {
                    val userLocation = getLastLocationOffline()
                    if (userLocation == null){
                        getUserLocation { currentLocation ->
                            if (currentLocation != null) {
                                mostrarRutaDesdeJSON(currentLocation, marker.position)
                                infoWindow.close()
                            } else {
                                Toast.makeText(requireContext(), "No se pudo obtener tu ubicación", Toast.LENGTH_SHORT).show()
                            }
                        }
                    return@setOnClickListener
                    } else {
                        mostrarRutaDesdeJSON(userLocation, marker.position)
                        infoWindow.close()
                    }
                }
                infoWindow.view.findViewById<Button>(R.id.btnCerrar).setOnClickListener {
                    infoWindow.close()
                }
                marker.showInfoWindow()
                marker.infoWindow = infoWindow
            }
        } ?: run {
            Toast.makeText(requireContext(), "Sitio no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarRutasDesdeJSON(): List<Triple<String, GeoPoint, List<GeoPoint>>> {
        val rutas = mutableListOf<Triple<String, GeoPoint, List<GeoPoint>>>()
        try {
            val inputStream = requireContext().assets.open("rutas.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            val rutasArray = jsonObject.getJSONArray("rutas")

            for (i in 0 until rutasArray.length()) {
                try {
                    val ruta = rutasArray.getJSONObject(i)
                    val origen = ruta.getJSONArray("origen")
                    val destino = ruta.getJSONArray("destino")
                    val puntosArray = ruta.getJSONArray("puntos")
                    val puntos = mutableListOf<GeoPoint>()

                    for (j in 0 until puntosArray.length()) {
                        val punto = puntosArray.getJSONArray(j)
                        puntos.add(GeoPoint(punto.getDouble(0), punto.getDouble(1)))
                    }

                    val origenPoint = GeoPoint(origen.getDouble(0), origen.getDouble(1))
                    val destinoPoint = GeoPoint(destino.getDouble(0), destino.getDouble(1))
                    rutas.add(Triple(ruta.getString("nombre"), origenPoint, puntos + destinoPoint))
                } catch (e: Exception) {
                    Log.e("cargarRutasDesdeJSON", "Error al procesar la ruta en índice $i", e)
                }
            }
        } catch (e: Exception) {
            Log.e("cargarRutasDesdeJSON", "Error al cargar rutas desde JSON", e)
        }
        return rutas
    }

    private var rutaPolyline: Polyline? = null

    private fun mostrarRutaDesdeJSON(userLocation: GeoPoint, destino: GeoPoint) {
        val rutas = cargarRutasDesdeJSON()
        val rutaSeleccionada = rutas.minByOrNull { ruta ->
            ruta.third.minOfOrNull { punto -> punto.distanceToAsDouble(destino) } ?: Double.MAX_VALUE
        }

        if (rutaSeleccionada != null) {
            val rutaCompleta = mutableListOf(userLocation)
            rutaCompleta.addAll(rutaSeleccionada.third)
            rutaPolyline?.let { mapView.overlayManager.remove(it) }
            rutaPolyline = Polyline(mapView).apply {
                outlinePaint.color = Color.GREEN
                outlinePaint.strokeWidth = 8f
                setPoints(rutaCompleta)
            }
            mapView.overlayManager.add(rutaPolyline!!)
            mapView.invalidate()
            Toast.makeText(requireContext(), "Ruta mostrada: ${rutaSeleccionada.first}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "No se encontró una ruta cercana al destino.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarRuta() {
        rutaPolyline?.let {
            mapView.overlayManager.remove(it)
            rutaPolyline = null
            mapView.invalidate()
        }
    }

    private fun navigateToDetalleSitioFragment(sitioNombre: String){
        val action = HomeFragmentDirections.actionNavHomeToDetalleSitioFragment(sitioNombre)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mapView.onDetach()
    }

    companion object {
        private const val REQUEST_WRITE_PERMISSION = 1
    }
}