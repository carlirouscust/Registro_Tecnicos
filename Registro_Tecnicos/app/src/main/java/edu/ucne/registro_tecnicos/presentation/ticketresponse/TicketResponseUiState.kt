package edu.ucne.registro_tecnicos.presentation.ticketresponse

import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity

data class TicketResponseUiState(
    val responseId: Int = 0,
    val ticketId: Int,
    val nombre: String,
    val mensaje: String,
    val fecha: String,
    val tipoUsuario: String,
    val ticketResponse: List<TicketResponseEntity> = emptyList()
)
