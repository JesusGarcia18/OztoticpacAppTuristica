package databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.oztoticpacappturistica.directorio.ContactoSitio

@Dao
    interface SitioDao{
        @Query("SELECT * FROM sitios")
        suspend fun getAllSitios(): List<Sitio>

        @Query("SELECT * FROM sitios WHERE id = :id")
        suspend fun getSitioById(id: Int): Sitio?

        @Query("SELECT * FROM sitios WHERE nombre = :nombre")
        suspend fun getSitioByName(nombre: String): Sitio?

        @Query("SELECT * FROM sitios WHERE categoria = :categoria")
        suspend fun obtenerSitiosPorCategoria(categoria: String): List<Sitio>

        @Query("SELECT * FROM sitios WHERE nombre LIKE :query OR categoria LIKE :query")
        suspend fun buscarSitios(query: String): List<Sitio>

        @Query("SELECT nombreContacto, contactos FROM sitios WHERE contactos IS NOT NULL")
        fun getContactosSitios(): LiveData<List<ContactoSitio>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun  insertSitio(sitio: Sitio)

        @Update
        suspend fun updateSitio(sitio: Sitio)

        @Delete
        suspend fun deleteSitio(sitio: Sitio)
    }