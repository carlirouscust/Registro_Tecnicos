package edu.ucne.registro_tecnicos.data.local.dao

import androidx.room.*
import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(response: TicketResponseEntity)

    @Query("SELECT * FROM ticket_responses WHERE ticketId = :ticketId ORDER BY responseId ASC")
    fun getResponsesByTicket(ticketId: Int): Flow<List<TicketResponseEntity>>

    @Query("DELETE FROM ticket_responses WHERE ticketId = :ticketId")
    suspend fun deleteResponsesByTicketId(ticketId: Int)

}