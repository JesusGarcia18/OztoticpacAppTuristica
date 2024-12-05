package databases

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

    @Entity(tableName = "sitios")
    @Parcelize
    data class Sitio(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val nombre: String,
        val categoria: String,
        val informacion: String,
        val historia: String? = null,
        val costos: String? = null,
        val horarios: String,
        val menu: String? = null,
        val servicios: String? = null,
        val contactos: String? = null,
        val nombreContacto: String? = null,
        val latitude: Double,
        val longitude: Double,
    ) : Parcelable