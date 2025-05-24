package edu.ucne.registro_tecnicos.presentation.ticketresponse

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketResponseScreen(
    ticketId: Int,
    viewModel: TicketResponseViewModel,
    getNombrePorTipo: (ticketId: Int, tipo: String) -> String,
    onBack: () -> Unit
) {
    val responses by viewModel.responses.collectAsState()
    var mensaje by remember { mutableStateOf("") }
    var tipoUsuario by remember { mutableStateOf("Usuario") }

    // Actualiza el nombre según el tipo seleccionado y el ticket
    val nombreUsuario = remember(ticketId, tipoUsuario) {
        getNombrePorTipo(ticketId, tipoUsuario)
    }

    // Cargar respuestas cuando cambie el ticketId
    LaunchedEffect(ticketId) { viewModel.loadResponses(ticketId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Respuestas al Ticket #$ticketId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Lista de respuestas (más reciente arriba)
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = true
            ) {
                items(responses.sortedByDescending { it.fecha }) { respuesta ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = respuesta.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = respuesta.fecha,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = respuesta.mensaje,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = respuesta.tipoUsuario,
                                color = if (respuesta.tipoUsuario == "Operador") Color.Green else Color.Blue,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // Área para nueva respuesta
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Agregar respuesta", fontWeight = FontWeight.Bold)

                // RadioButtons para tipo de usuario
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = tipoUsuario == "Usuario",
                        onClick = { tipoUsuario = "Usuario" }
                    )
                    Text("Usuario")
                    Spacer(Modifier.width(16.dp))
                    RadioButton(
                        selected = tipoUsuario == "Operador",
                        onClick = { tipoUsuario = "Operador" }
                    )
                    Text("Operador")
                }

                OutlinedTextField(
                    value = nombreUsuario,
                    onValueChange = {},
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false
                )

                OutlinedTextField(
                    value = mensaje,
                    onValueChange = { mensaje = it },
                    label = { Text("Tu mensaje") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp), // Más alto para mensajes largos
                    singleLine = false,
                    maxLines = 6
                )

                Button(
                    onClick = {
                        if (mensaje.isNotBlank()) {
                            viewModel.sendResponse(
                                ticketId = ticketId,
                                nombre = nombreUsuario,
                                mensaje = mensaje,
                                tipoUsuario = tipoUsuario
                            )
                            mensaje = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Enviar respuesta")
                }
            }
        }
    }
}