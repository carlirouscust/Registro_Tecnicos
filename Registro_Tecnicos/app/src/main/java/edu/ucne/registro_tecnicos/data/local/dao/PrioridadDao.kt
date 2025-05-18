package edu.ucne.registro_tecnicos.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ucne.registro_tecnicos.data.local.entities.PrioridadEntity

@Dao
interface PrioridadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPrioridades(prioridades: List<PrioridadEntity>)

    @Query("SELECT * FROM prioridades")
    suspend fun getPrioridades(): List<PrioridadEntity>
}
