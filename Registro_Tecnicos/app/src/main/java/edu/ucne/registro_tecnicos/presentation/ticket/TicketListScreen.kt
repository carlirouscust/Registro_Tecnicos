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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import edu.ucne.registro_tecnicos.data.local.entities.TecnicosEntity
import edu.ucne.registro_tecnicos.data.local.entities.TicketsEntity

@Composable
fun TicketListScreen(
    navController: NavController,
    ticketList: List<TicketsEntity>,
    tecnicos: List<TecnicosEntity>,
    onDelete: (TicketsEntity) -> Unit,
    onComment: (TicketsEntity) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("Ticket/null") },
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
                    TicketRow(
                        ticket = ticket,
                        tecnicos = tecnicos,
                        onDelete = onDelete,
                        onEdit = { selectedTicket ->
                            navController.navigate("Ticket/${selectedTicket.ticketId}")
                        },
                        onComment = { selectedTicket ->
                            navController.navigate("TicketResponse/${selectedTicket.ticketId}")
                        }
                    )

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
    onEdit: (TicketsEntity) -> Unit,
    onComment: (TicketsEntity) -> Unit
) {
    val prioridadTexto = when (ticket.prioridadId) {
        1 -> "Baja"
        2 -> "Media"
        3 -> "Alta"
        else -> "Desconocida"
    }

    val tecnicoNombre = tecnicos.find { it.tecnicosId == ticket.tecnicoId }?.nombre ?: "Desconocido"

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                InfoRow(label = "Fecha:", value = ticket.fecha)
                InfoRow(label = "Prioridad:", value = prioridadTexto)
                InfoRow(label = "Cliente:", value = ticket.cliente)
                InfoRow(label = "Asunto:", value = ticket.asunto)

                // Descripción mejorada
                Row(verticalAlignment = Alignment.Top) {
                    Text("Descripción:", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = ticket.descripcion.replace("\n", " "),
                        modifier = Modifier.weight(1f),
                        softWrap = true,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                InfoRow(label = "Técnico:", value = tecnicoNombre)
            }

            ActionButtons(
                onComment = { onComment(ticket) },
                onEdit = { onEdit(ticket) },
                onDelete = { onDelete(ticket) }
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(4.dp))
        Text(value)
    }
}

@Composable
private fun ActionButtons(
    onComment: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column(horizontalAlignment = Alignment.End) {
        IconButton(onClick = onComment) {
            Icon(Icons.Filled.ChatBubble, "Comentar", tint = Color(0xFF4CAF50))
        }
        IconButton(onClick = onEdit) {
            Icon(Icons.Filled.Edit, "Editar", tint = Color(0xFF4CAF50))
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, "Eliminar", tint = Color.Red)
        }
    }
}
