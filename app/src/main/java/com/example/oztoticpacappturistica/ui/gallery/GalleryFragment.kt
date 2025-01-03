package com.example.oztoticpacappturistica.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R
import com.example.oztoticpacappturistica.SitioRepository
import com.example.oztoticpacappturistica.SitiosCategoriaAdapter
import databases.AppDatabase
import databases.Sitio
import kotlinx.coroutines.runBlocking

class GalleryFragment : Fragment() {

    private val imagenesSitios = mapOf(
        "Laguna de Nogales" to R.drawable.laguna
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        val sitios = obtenerHistoricos()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.adapter = SitiosCategoriaAdapter(sitios,{ sitio ->
            val bundle = bundleOf("sitioNombre" to sitio.nombre)
            findNavController().navigate(R.id.action_historicosFragment_to_detalleSitioFragment, bundle)
        }, imagenesSitios)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        return view
    }

    private fun obtenerHistoricos(): List<Sitio> {
        val sitioRepository = SitioRepository(AppDatabase.getInstance(requireContext()).sitioDao())
        return runBlocking {
            sitioRepository.obtenerSitiosPorCategoria("Historicos")
        }
    }
}