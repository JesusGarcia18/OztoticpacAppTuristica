package com.example.oztoticpacappturistica.settings

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val options = listOf(
            SettingOption(
                iconResId = R.drawable.baseline_settings_24,
                title = "Modo Claro/Oscuro",
                description = "Alterna entre modo oscuro o modo claro"
            ){
                toggleTheme()
            },
            SettingOption(
                iconResId = R.drawable.baseline_place_24,
                title = "Permisos de Ubicación",
                description = "Activa o desactiva los permisos de ubicación"
            ){
                checkPermission()
            },
            SettingOption(
                iconResId = R.drawable.baseline_downloading_24,
                title = "Gestion de Mapas Offline",
                description = "Descargar o Actualizar Mapas Offline"
            ){
                manageOfflineMaps()
            }
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.rvSettings)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = SettingsAdapter(options)
        return view
    }

    private fun toggleTheme() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val newNightMode = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_NO
            Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(newNightMode)
        val prefs = requireContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putInt("theme_mode", newNightMode)
            apply()
        }

        Toast.makeText(requireContext(), "Tema cambiado", Toast.LENGTH_SHORT).show()
    }

    private fun checkPermission() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", requireContext().packageName, null)
        }
        startActivity(intent)

        Toast.makeText(requireContext(), "Verifica los permisos de la aplicación", Toast.LENGTH_SHORT).show()
    }

    private fun manageOfflineMaps() {
        findNavController().navigate(R.id.action_settingsFragment_to_manageMapsFragment)
    }

}