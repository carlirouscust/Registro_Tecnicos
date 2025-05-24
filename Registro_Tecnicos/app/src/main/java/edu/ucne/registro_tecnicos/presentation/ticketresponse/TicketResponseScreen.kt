package edu.ucne.registro_tecnicos.presentation.ticketresponse

import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketResponseScreen(
    ticketId: Int,
    responses: List<TicketResponseEntity>,
    nombreUsuario: String,
    tipoUsuario: String,
    onMessageSend: (String) -> Unit,
    onBack: () -> Unit
) {
    var mensaje by remember { mutableStateOf("") }

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
            // Lista de respuestas
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(responses) { respuesta ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = respuesta.nombre,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = respuesta.fecha)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = respuesta.mensaje)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = respuesta.tipoUsuario,
                                color = if (respuesta.tipoUsuario == "Operador") Color.Green else Color.Blue
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
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (mensaje.isNotBlank()) {
                            onMessageSend(mensaje)
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