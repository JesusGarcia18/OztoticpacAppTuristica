package com.example.oztoticpacappturistica.directorio

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

class Directorio : Fragment() {

    private lateinit var directorioViewModel: DirectorioViewModel
    private lateinit var adapter: ContactoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_directorio, container, false)

        directorioViewModel = ViewModelProvider(this)[DirectorioViewModel::class.java]

        val recyclerView: RecyclerView = view.findViewById(R.id.rvDirectorio)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ContactoAdapter(emptyList()) { numero ->
            copiarAlPortapapeles(numero)
            Toast.makeText(requireContext(), "Numero copiado", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        directorioViewModel.sitiosConContactos.observe(viewLifecycleOwner){ contactos ->
            val listaContactos = contactos
                .filter { it.nombreContacto?.isNotBlank() == true && it.contactos?.isNotBlank() == true }
                .map { contacto ->
                ContactoSitio(
                    nombreContacto = contacto.nombreContacto ?: "Nombre no disponible",
                    contactos = contacto.contactos ?: "Sin numero")
            }
            adapter.updateData(listaContactos)
        }
        return view
    }

    private fun copiarAlPortapapeles(texto: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Contacto", texto)
        clipboard.setPrimaryClip(clip)
    }
}