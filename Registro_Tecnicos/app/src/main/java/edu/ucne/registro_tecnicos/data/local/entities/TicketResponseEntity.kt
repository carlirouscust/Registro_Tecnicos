package edu.ucne.registro_tecnicos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticket_responses")

data class TicketResponseEntity(
    @PrimaryKey(autoGenerate = true)
    val responseId: Int = 0,
    val ticketId: Int,
    val nombre: String,
    val mensaje: String,
    val fecha: String,
    val tipoUsuario: String
)
