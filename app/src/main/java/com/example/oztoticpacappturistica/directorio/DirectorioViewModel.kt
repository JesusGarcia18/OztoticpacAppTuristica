package com.example.oztoticpacappturistica.directorio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.oztoticpacappturistica.SitioRepository
import databases.AppDatabase

class DirectorioViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SitioRepository
    val sitiosConContactos: LiveData<List<ContactoSitio>>


    init {
        val sitioDao = AppDatabase.getDatabase(application).sitioDao()
        repository = SitioRepository(sitioDao)
        sitiosConContactos = repository.obtenerContactosDeSitios()
    }
}