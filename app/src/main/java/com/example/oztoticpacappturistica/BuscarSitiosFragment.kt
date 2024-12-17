package com.example.oztoticpacappturistica

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import databases.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.os.bundleOf


class BuscarSitiosFragment : DialogFragment() {

    private lateinit var adapter: SitiosAdapter
    private lateinit var db: AppDatabase

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_buscar_sitios, null)

        val etBuscarSitio = view.findViewById<EditText>(R.id.etBuscarSitio)
        val rvResultadosBusqueda = view.findViewById<RecyclerView>(R.id.rvResultadosBusqueda)

        adapter = SitiosAdapter { sitio ->
            dialog?.dismiss()
            val bundle = bundleOf("sitioNombre" to sitio.nombre)
            findNavController().navigate(R.id.detalleSitioFragment, bundle)
        }

        rvResultadosBusqueda.layoutManager = LinearLayoutManager(requireContext())
        rvResultadosBusqueda.adapter = adapter

        db = AppDatabase.getInstance(requireContext())

        etBuscarSitio.addTextChangedListener { text ->
            lifecycleScope.launch {
                val resultados = withContext(Dispatchers.IO) {
                    db.sitioDao().buscarSitios("%${text.toString()}%")
                        .take(3)
                }
                adapter.updateList(resultados)
            }
        }

        builder.setView(view)
        return builder.create()
    }
}