package edu.ucne.registro_tecnicos.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity
import edu.ucne.registro_tecnicos.data.repository.TicketRepository
import kotlinx.coroutines.flow.stateIn
import edu.ucne.registro_tecnicos.data.repository.TicketResponseRepository

class TicketViewModel(
    private val repository: TicketRepository,
    private val ticketResponseRepository: TicketResponseRepository
) : ViewModel() {

    val ticketList: StateFlow<List<TicketsEntity>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val prioridades: List<Pair<Int, String>> = listOf(
        1 to "Alta",
        2 to "Media",
        3 to "Baja"
    )

    fun agregarTicket(fecha: String, prioridadId: Int, cliente: String, asunto: String, descripcion: String, tecnicoId: Int) {
        val ticket = TicketsEntity(
            ticketId = null,
            fecha = fecha,
            prioridadId = null,
            cliente = cliente,
            asunto = asunto,
            descripcion = descripcion,
            tecnicoId = null
        )
        saveTitcket(ticket)
    }

    fun saveTitcket(ticket: TicketsEntity) {
        viewModelScope.launch {
            repository.saveTicket(ticket)
        }
    }

    fun delete(tecnico: TicketsEntity) {
        viewModelScope.launch {
            repository.delete(tecnico)
        }
    }

    fun update(ticket: TicketsEntity) {
        saveTitcket(ticket)
    }

    fun getTicketById(ticketId: Int?): TicketsEntity? {
        return ticketList.value.find { it.ticketId == ticketId }
    }
}