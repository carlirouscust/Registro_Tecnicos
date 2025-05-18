package edu.ucne.registro_tecnicos.presentation.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registro_tecnicos.data.local.entities.TecnicosEntity
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity

@Composable
fun TicketListScreen(
    ticketList: List<TicketsEntity>,
    tecnicos: List<TecnicosEntity>,
    onCreate: () -> Unit,
    onDelete: (TicketsEntity) -> Unit,
    onEdit: (TicketsEntity) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreate,
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Lista de Ticket",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 39.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                items(ticketList) { ticket ->
                    TicketRow(ticket, tecnicos, onDelete, onEdit)
                }
            }
        }
    }
}

@Composable
fun TicketRow(
    ticket: TicketsEntity,
    tecnicos: List<TecnicosEntity>,
    onDelete: (TicketsEntity) -> Unit,
    onEdit: (TicketsEntity) -> Unit
) {
    val prioridadTexto = when (ticket.prioridadId) {
        1 -> "Baja"
        2 -> "Media"
        3 -> "Alta"
        else -> "Desconocida"
    }

    val tecnicoNombre = tecnicos.find { tecnico -> tecnico.tecnicosId == ticket.tecnicoId }?.nombre ?: "Desconocido"

    Card(
        elevation = CardDefaults.cardElevation(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(22.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Fecha: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = ticket.fecha, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Prioridad: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = prioridadTexto, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Cliente: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = ticket.cliente, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Asunto: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = ticket.asunto, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = "Descripcion: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = ticket.descripcion,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .weight(1f),
                        softWrap = true
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Técnico: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = tecnicoNombre, fontSize = 16.sp)
                }

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { onEdit(ticket) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(28.dp)
                    )
                }

                IconButton(
                    onClick = { onDelete(ticket) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}