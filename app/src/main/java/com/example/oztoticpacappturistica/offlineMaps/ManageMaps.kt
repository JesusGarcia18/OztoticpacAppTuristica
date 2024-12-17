package com.example.oztoticpacappturistica.offlineMaps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.oztoticpacappturistica.R
import java.io.File
import android.widget.Toast


class ManageMaps : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_maps, container, false)

        val tvMapStatus = view.findViewById<TextView>(R.id.tvMapStatus)
        val btnDownloadMap = view.findViewById<Button>(R.id.btnDownloadMap)
        val btnUpdateMap = view.findViewById<Button>(R.id.btnUpdateMap)

        val isMapDownloaded = checkMapStatus()
        tvMapStatus.text = if (isMapDownloaded) "El mapa está descargado" else "El mapa no está descargado"

        btnDownloadMap.setOnClickListener {
            downloadOfflineMap()
            tvMapStatus.text = getString(R.string.el_mapa_est_descargado)
        }

        btnUpdateMap.setOnClickListener {
            updateOfflineMap()
            tvMapStatus.text = getString(R.string.el_mapa_est_actualizado)

        }
        return view
    }

    private fun checkMapStatus(): Boolean {
        val mapFile = File(requireContext().getExternalFilesDir(null), "nogales_tiles")
        return mapFile.exists()
    }

    private fun downloadOfflineMap() {
        Toast.makeText(requireContext(), "Descargando mapas offline...", Toast.LENGTH_SHORT).show()
    }

    private fun updateOfflineMap() {
        Toast.makeText(requireContext(), "Actualizando mapas offline...", Toast.LENGTH_SHORT).show()
    }

}