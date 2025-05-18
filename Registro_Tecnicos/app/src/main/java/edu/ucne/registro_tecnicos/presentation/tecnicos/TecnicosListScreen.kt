package edu.ucne.registro_tecnicos.presentation.tecnicos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registro_tecnicos.data.local.entities.TecnicosEntity

@Composable
fun TecnicoListScreen(
    tecnicoList: List<TecnicosEntity>,
    onCreate: () -> Unit,
    onDelete: (TecnicosEntity) -> Unit,
    onEdit: (TecnicosEntity) -> Unit
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
                text = "Lista de Técnicos",
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
                items(tecnicoList) { tecnico ->
                    TecnicoRow(tecnico, onDelete, onEdit)
                }
            }
        }
    }
}

@Composable
fun TecnicoRow(
    tecnico: TecnicosEntity,
    onDelete: (TecnicosEntity) -> Unit,
    onEdit: (TecnicosEntity) -> Unit
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
                    Text(text = "Nombre: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = tecnico.nombre, fontSize = 16.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Sueldo: ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "RD$${tecnico.sueldo}", fontSize = 16.sp)
                }
            }

            Row {
                IconButton(onClick = { onEdit(tecnico) }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar", tint = Color(0xFF4CAF50))
                }
                IconButton(onClick = { onDelete(tecnico) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TecnicoListScreenPreview() {
    val sampleTecnicos = remember {
        mutableStateListOf(
            TecnicosEntity(tecnicosId = 1, nombre = "Juan Pérez", sueldo = 25000.0),
            TecnicosEntity(tecnicosId = 2, nombre = "María García", sueldo = 28000.0),
            TecnicosEntity(tecnicosId = 3, nombre = "Carlos López", sueldo = 32000.0)
        )
    }


    TecnicoListScreen(
        tecnicoList = sampleTecnicos,
        onCreate = { sampleTecnicos.add(TecnicosEntity(tecnicosId = 1,nombre = "Nuevo Técnico", sueldo = 30000.0)) },
        onDelete = { tecnico -> sampleTecnicos.remove(tecnico) },
        onEdit = { /* Simulación de edición */ }
    )
}