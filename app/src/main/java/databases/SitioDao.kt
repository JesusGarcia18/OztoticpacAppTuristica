package databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

    @Dao
    interface SitioDao{
        @Query("SELECT * FROM sitios")
        fun getAllSitios(): List<Sitio>

        @Query("SELECT * FROM sitios WHERE id = :sitioId")
        suspend fun getSitioById(sitioId: Int): Sitio?

        @Query("SELECT * FROM sitios WHERE categoria = :categoria")
        suspend fun getSitiosPorCategoria(categoria: String): List<Sitio>

        @Query("SELECT * FROM sitios WHERE nombre LIKE :query")
        fun buscarSitiosPorNombre(query: String): List<Sitio>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun  insertSitio(sitio: Sitio)

        @Update
        suspend fun updateSitio(sitio: Sitio)

        @Delete
        suspend fun deleteSitio(sitio: Sitio)
    }