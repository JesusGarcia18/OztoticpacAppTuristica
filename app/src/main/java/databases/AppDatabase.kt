package databases

import androidx.room.Database
import androidx.room.RoomDatabase

    @Database(entities = [Sitio::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase(){
        abstract fun sitioDao(): SitioDao
    }
