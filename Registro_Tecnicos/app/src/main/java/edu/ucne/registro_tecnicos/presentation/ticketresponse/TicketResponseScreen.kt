package edu.ucne.registro_tecnicos.presentation.ticketresponse

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity
import java.util.*
import androidx.compose.material3.ButtonDefaults

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

    val nombreUsuario = remember(ticketId, tipoUsuario) {
        getNombrePorTipo(ticketId, tipoUsuario)
    }

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
                .background(Color.Gray)
                .padding(padding)
                .padding(16.dp)
        ) {
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
                            containerColor = Color.DarkGray
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
                                    color = Color.White
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Agregar respuesta", fontWeight = FontWeight.Bold, color = Color.White)

                    // RadioButtons para tipo de usuario
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoUsuario == "Usuario",
                            onClick = { tipoUsuario = "Usuario" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Green,
                                unselectedColor = Color.White
                            )
                        )
                        Text("Cliente", color = Color.White)
                        Spacer(Modifier.width(16.dp))
                        RadioButton(
                            selected = tipoUsuario == "Operador",
                            onClick = { tipoUsuario = "Operador" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Green,
                                unselectedColor = Color.White
                            )
                        )
                        Text("Tecnico", color = Color.White)
                    }

                    OutlinedTextField(
                        value = nombreUsuario,
                        onValueChange = {},
                        label = { Text("Nombre", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            disabledTextColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            disabledBorderColor = Color.Gray,
                            disabledLabelColor = Color.White,
                            disabledPlaceholderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            cursorColor = Color.White
                        )
                    )

                    OutlinedTextField(
                        value = mensaje,
                        onValueChange = { mensaje = it },
                        label = { Text("Tu mensaje", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp), // Más alto para mensajes largos
                        singleLine = false,
                        maxLines = 6,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            cursorColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White
                        )
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
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Enviar respuesta")
                    }
                }
            }
        }
    }
}