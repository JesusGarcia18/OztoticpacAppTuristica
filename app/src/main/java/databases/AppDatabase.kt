package databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

    @Database(entities = [Sitio::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase(){
        abstract fun sitioDao(): SitioDao

        companion object {
            @Volatile
            private var INSTANCE: AppDatabase? = null

            fun getInstance(context: Context): AppDatabase{
                synchronized(this){
                    var instance = INSTANCE

                    if (instance == null){
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "sitios-database"
                        ).build()
                        INSTANCE = instance
                    }
                    return instance
                }
            }

            fun getDatabase(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"sitios-database").build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }
