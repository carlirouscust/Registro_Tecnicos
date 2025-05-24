package edu.ucne.registro_tecnicos.data.repository

import edu.ucne.registro_tecnicos.data.local.dao.TicketResponseDao
import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity
import kotlinx.coroutines.flow.Flow

class TicketResponseRepository(private val dao: TicketResponseDao) {
    suspend fun insert(response: TicketResponseEntity) = dao.insert(response)

    fun getResponsesByTicket(ticketId: Int): Flow<List<TicketResponseEntity>> =
        dao.getResponsesByTicket(ticketId)

}