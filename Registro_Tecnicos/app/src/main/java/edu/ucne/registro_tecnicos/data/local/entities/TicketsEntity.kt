package edu.ucne.registro_tecnicos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tickets")

data class TicketsEntity (

    @PrimaryKey
    val ticketId: Int? = null,
    val fecha: String = "",
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val prioridadId: Int? = null,
    val tecnicoId: Int? = null,
)