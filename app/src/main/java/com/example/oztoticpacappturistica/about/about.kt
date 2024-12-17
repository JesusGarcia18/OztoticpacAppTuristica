package com.example.oztoticpacappturistica.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

class About : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)

    val textoInfo = listOf(
        TextoInfo("Esta aplicación es una guía turística que permite explorar sitios de Nogales, Veracruz, con mapas offline, detalles históricos y más.")
    )

        val recyclerView: RecyclerView = view.findViewById(R.id.rvInfo)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = AboutAdapter(textoInfo)

        return view
    }

}