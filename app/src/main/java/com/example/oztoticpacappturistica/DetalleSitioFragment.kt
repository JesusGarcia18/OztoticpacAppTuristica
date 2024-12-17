package com.example.oztoticpacappturistica

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.example.oztoticpacappturistica.databinding.FragmentDetalleSitioBinding
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import databases.AppDatabase
import databases.Sitio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetalleSitioFragment : Fragment() {

    private var _binding: FragmentDetalleSitioBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "sitios-database"
        ).build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleSitioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val sitioNombre = arguments?.getString("sitioNombre")

        sitioNombre?.let { nombre ->
            cargarDetallesSitio(nombre)
        }
    }

    private fun configurarToolbar(sitio: Sitio){
        val iconGallery = getIconoCategoria(sitio.categoria)
        (requireActivity() as MainActivity).updateToolbar(sitio.nombre, iconGallery)
    }

    private fun getIconoCategoria(categoria: String): Int{
        return when(categoria){
            "Naturales-Culturales" -> R.drawable.baseline_park_24
                "Historicos" -> R.drawable.baseline_museum_24
                    "Gastronomicos" -> R.drawable.baseline_restaurant_24
                        else -> R.drawable.ic_launcher_foreground
        }
    }

    private fun cargarDetallesSitio(sitioNombre: String) {

        lifecycleScope.launch {
            val sitio = withContext(Dispatchers.IO){
                db.sitioDao().getSitioByName(sitioNombre)
            }

            sitio?.let {
                withContext(Dispatchers.Main){
                    // Rellenar la información en la vista
                    configurarToolbar(it)
                    binding.nombreSitio.text = it.nombre
                    binding.informacionSitio.text = it.informacion
                    binding.historiaSitio.text = it.historia
                    binding.costosSitio.text = it.costos
                    binding.horariosSitio.text = it.horarios
                    if (it.menu.isNullOrEmpty()){
                        binding.menuSitio.visibility = View.GONE
                    } else {
                        binding.menuSitio.text = it.menu
                        binding.menuSitio.visibility = View.VISIBLE
                    }
                    if (it.servicios.isNullOrEmpty()){
                        binding.serviciosSitio.visibility = View.GONE
                    } else {
                        binding.serviciosSitio.text = it.servicios
                        binding.serviciosSitio.visibility = View.VISIBLE
                    }
                    if (it.nombreContacto.isNullOrEmpty()){
                        binding.nombreContacto.visibility = View.GONE
                    } else {
                        binding.nombreContacto.text = it.nombreContacto
                        binding.nombreContacto.visibility = View.VISIBLE
                    }
                    if (it.contactos.isNullOrEmpty()){
                        binding.contactosSitio.visibility = View.GONE
                    } else {
                        binding.contactosSitio.text = it.contactos
                        binding.contactosSitio.visibility = View.VISIBLE
                    }

                    // Configurar imagen del sitio
                    val imagenResId = imagenesSitios[it.nombre] ?: R.drawable.ic_menu_gallery
                    binding.imagenSitio.setImageResource(imagenResId)
                    binding.imagenSitio.contentDescription = "Imagen de ${it.nombre}"

                }

            }
        }
    }

    // Mapa de imágenes según el nombre del sitio
    private val imagenesSitios = mapOf(
        "Laguna de Nogales" to R.drawable.laguna,
        // Agrega más sitios e imágenes según sea necesario
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}