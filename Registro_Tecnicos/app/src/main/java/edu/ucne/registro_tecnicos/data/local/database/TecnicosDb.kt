package edu.ucne.registro_tecnicos.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.ucne.registro_tecnicos.data.local.dao.PrioridadDao
import edu.ucne.registro_tecnicos.data.local.dao.TicketDao
import edu.ucne.registro_tecnicos.data.local.dao.TicketResponseDao
import edu.ucne.registro_tecnicos.data.local.dao.TecnicosDao
import edu.ucne.registro_tecnicos.data.local.entities.PrioridadEntity
import edu.ucne.registro_tecnicos.data.local.entities.TecnicosEntity
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity
import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        TecnicosEntity::class,
        TicketsEntity::class,
        PrioridadEntity::class,
        TicketResponseEntity::class
    ],
    version = 12,
    exportSchema = false
)
abstract class TecnicosDb : RoomDatabase() {
    abstract fun tecnicosDao(): TecnicosDao
    abstract fun TicketDao(): TicketDao
    abstract fun PrioridadDao(): PrioridadDao
    abstract fun ticketResponseDao(): TicketResponseDao

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
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
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