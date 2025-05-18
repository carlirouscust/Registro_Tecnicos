package edu.ucne.registro_tecnicos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import edu.ucne.registro_tecnicos.data.local.entities.TecnicosEntity

@Dao
interface TecnicosDao {
    @Upsert()
    suspend fun save(Tecnico: TecnicosEntity)

    @Query(
        """
        SELECT * 
        FROM Tecnicos 
        WHERE tecnicosId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): TecnicosEntity?

    @Delete
    suspend fun delete(Tecnico: TecnicosEntity)

    @Query("SELECT * FROM Tecnicos")
    fun getAll(): Flow<List<TecnicosEntity>>
}