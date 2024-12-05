package com.example.oztoticpacappturistica

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.oztoticpacappturistica.databinding.FragmentDetalleSitioBinding
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import databases.AppDatabase

class DetalleSitioFragment : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var binding: FragmentDetalleSitioBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding
        binding = FragmentDetalleSitioBinding.bind(view)

        // Obtener el ID del sitio desde los argumentos
        val sitioId = arguments?.getInt("sitioId", -1) ?: -1

        // Inicializar base de datos
        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "sitios-database"
        ).build()

        if (sitioId != -1) {
            cargarDetallesSitio(sitioId)
        }
    }

    private fun cargarDetallesSitio(sitioId: Int) {
        lifecycleScope.launch {
            val sitio = db.sitioDao().getSitioById(sitioId)

            sitio?.let {
                // Rellenar la información en la vista
                binding.nombreSitio.text = it.nombre
                binding.informacionSitio.text = it.informacion
                binding.historiaSitio.text = it.historia
                binding.costosSitio.text = it.costos
                binding.horariosSitio.text = it.horarios
                binding.menuSitio.text = it.menu
                binding.serviciosSitio.text = it.servicios
                binding.nombreContacto.text = it.nombreContacto
                binding.contactosSitio.text = it.contactos

                // Configurar imagen del sitio
                val imagenResId = imagenesSitios[it.nombre] ?: R.drawable.ic_menu_gallery
                binding.imagenSitio.setImageResource(imagenResId)
                binding.imagenSitio.contentDescription = "Imagen de ${it.nombre}"

                // Descripciones de accesibilidad
                binding.nombreSitio.contentDescription = "Nombre del sitio: ${it.nombre}"
                binding.informacionSitio.contentDescription = "Información del sitio: ${it.informacion}"
                binding.historiaSitio.contentDescription = "Historia del sitio: ${it.historia}"
                binding.costosSitio.contentDescription = "Costos del sitio: ${it.costos}"
                binding.horariosSitio.contentDescription = "Horarios del sitio: ${it.horarios}"
                binding.menuSitio.contentDescription = "Menú del sitio: ${it.menu}"
                binding.serviciosSitio.contentDescription = "Servicios del sitio: ${it.servicios}"
                binding.nombreContacto.contentDescription = "Nombre del contacto: ${it.nombreContacto}"
                binding.contactosSitio.contentDescription = "Contactos del sitio: ${it.contactos}"
            }
        }
    }

    // Mapa de imágenes según el nombre del sitio
    private val imagenesSitios = mapOf(
        "Laguna de Nogales" to R.drawable.laguna,
        // Agrega más sitios e imágenes según sea necesario
    )

}