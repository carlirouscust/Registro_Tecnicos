package edu.ucne.registro_tecnicos.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.ucne.registro_tecnicos.data.local.dao.PrioridadDao
import edu.ucne.registro_tecnicos.data.local.dao.TicketDao
import edu.ucne.registro_tecnicos.data.local.entities.TecnicosEntity
import edu.ucne.registro_tecnicos.data.local.entities.PrioridadEntity
import edu.ucne.registro_tecnicos.data.local.dao.TecnicosDao
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        TecnicosEntity::class,
        TicketsEntity::class,
        PrioridadEntity::class
    ],
    version = 11,
    exportSchema = false
)

abstract class TecnicosDb : RoomDatabase() {
    abstract fun tecnicosDao(): TecnicosDao
    abstract fun TicketDao(): TicketDao
    abstract fun PrioridadDao(): PrioridadDao

    companion object {
        @Volatile
        private var INSTANCE: TecnicosDb? = null

        fun getDatabase(context: Context): TecnicosDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TecnicosDb::class.java,
                    "tecnicos_db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Lógica para insertar prioridades predeterminadas
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.PrioridadDao()?.insertarPrioridades(
                                    listOf(
                                        PrioridadEntity(1, "Baja"),
                                        PrioridadEntity(2, "Media"),
                                        PrioridadEntity(3, "Alta")
                                    )
                                )
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
