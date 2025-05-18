package edu.ucne.registro_tecnicos.data.repository

import edu.ucne.registro_tecnicos.data.local.database.TecnicosDb
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TicketRepository (private val tecnicoDb: TecnicosDb)
{
    suspend fun saveTicket(ticket: TicketsEntity){
        tecnicoDb.TicketDao().save(ticket)
    }
    suspend fun delete(ticket: TicketsEntity){
        return tecnicoDb.TicketDao().delete(ticket)
    }
    fun getTicketsTecnico(tecnicoId: Int): Flow<List<TicketsEntity>> {
        return tecnicoDb.TicketDao().getTicketsTecnico(tecnicoId)
    }
    suspend fun updateTicket(ticket: TicketsEntity) {
        tecnicoDb.TicketDao().update(ticket)
    }

    suspend fun find(id: Int): TicketsEntity?{
        return tecnicoDb.TicketDao().find(id)
    }

    fun getAll(): Flow<List<TicketsEntity>> {
        return tecnicoDb.TicketDao().getAll()
    }
}