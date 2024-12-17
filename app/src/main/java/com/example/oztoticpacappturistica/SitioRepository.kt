package com.example.oztoticpacappturistica

import androidx.lifecycle.LiveData
import com.example.oztoticpacappturistica.directorio.ContactoSitio
import databases.SitioDao
import databases.Sitio


class SitioRepository (private val sitioDao: SitioDao){

    suspend fun obtenerSitiosPorCategoria(categoria: String): List<Sitio> {
        return sitioDao.obtenerSitiosPorCategoria(categoria)

    }

    fun obtenerContactosDeSitios(): LiveData<List<ContactoSitio>>{
        return sitioDao.getContactosSitios()
    }
}