package edu.ucne.registro_tecnicos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Upsert
    suspend fun save(ticket: TicketsEntity)
    @Query("SELECT * FROM tickets WHERE tecnicoId = :tecnicoId")
    fun getTicketsTecnico(tecnicoId: Int): Flow<List<TicketsEntity>>

    @Query(
        """
            SELECT * FROM Tickets
            WHERE ticketId = :id
            LIMIT 1
        """)
    suspend fun find(id: Int): TicketsEntity?
    @Update
    suspend fun update(ticket: TicketsEntity)
    @Delete
    suspend fun delete(ticket: TicketsEntity)

    @Query("SELECT * FROM Tickets")
    fun getAll(): Flow<List<TicketsEntity>>
}