package edu.ucne.registro_tecnicos.data.local.repository

import edu.ucne.registro_tecnicos.data.local.dao.TicketResponseDao
import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TicketResponseRepository @Inject constructor (private val dao: TicketResponseDao) {
    suspend fun insert(response: TicketResponseEntity) = dao.insert(response)

    fun getResponsesByTicket(ticketId: Int): Flow<List<TicketResponseEntity>> =
        dao.getResponsesByTicket(ticketId)

}