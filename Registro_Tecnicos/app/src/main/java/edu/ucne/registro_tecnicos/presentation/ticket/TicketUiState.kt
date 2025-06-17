package edu.ucne.registro_tecnicos.presentation.ticket

import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity

data class TicketUiState (
    val ticketId: Int? = null,
    val fecha: String = "",
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val prioridadId: Int? = null,
    val tecnicoId: Int? = null,
    val ticket: List<TicketsEntity> = emptyList()

)