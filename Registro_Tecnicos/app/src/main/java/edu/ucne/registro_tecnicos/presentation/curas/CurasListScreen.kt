package edu.ucne.registro_tecnicos.presentation.curas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registro_tecnicos.presentation.remote.dto.curasDto

@Composable
fun CurasListScreen(
    state: CurasUiState,
    onCreate: () -> Unit,
    onDelete: (curasDto) -> Unit,
    onEdit: (curasDto) -> Unit
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
                text = "Lista de Curas",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 24.dp))

            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
            if (state.inputError != null) {
                Text(
                    text = state.inputError,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                items(state.curas) { cura ->
                    CuraRow(cura, onDelete, onEdit)
                }
            }
        }
    }
}

@Composable
fun CuraRow(
    cura: curasDto,
    onDelete: (curasDto) -> Unit,
    onEdit: (curasDto) -> Unit
) {
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
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Descripción: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = cura.descripcion ?: "", fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Monto: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "RD$${cura.monto}", fontSize = 16.sp)
                }
            }

            Row {
                IconButton(onClick = { onEdit(cura) }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar", tint = Color(0xFF4CAF50))
                }
                IconButton(onClick = { onDelete(cura) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurasListScreenPreview() {
    val sampleCuras = remember {
        mutableStateListOf(
            curasDto(curasId = 1, descripcion = "Cura menor", monto = 1000),
            curasDto(curasId = 2, descripcion = "Cura mayor", monto = 2500),
            curasDto(curasId = 3, descripcion = "Cura especial", monto = 4000)
        )
    }
    val state = CurasUiState(
        curas = sampleCuras
    )

    CurasListScreen(
        state = state,
        onCreate = { sampleCuras.add(
            curasDto(curasId = sampleCuras.size+1, descripcion = "Nueva Cura", monto = 2000)
        ) },
        onDelete = { cura -> sampleCuras.remove(cura) },
        onEdit = { /* Simulación de edición */ }
    )
}