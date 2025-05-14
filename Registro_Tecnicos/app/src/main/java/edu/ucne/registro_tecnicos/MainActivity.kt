package edu.ucne.registro_tecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import edu.ucne.registro_tecnicos.ui.theme.Registro_TecnicosTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var tecnicosDb: TecnicosDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tecnicosDb = Room.databaseBuilder(
            applicationContext,
            TecnicosDb::class.java,
            "tecnicos.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            Registro_TecnicosTheme {


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        TecnicosScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun TecnicosScreen(
    ) {
        var nombre by remember { mutableStateOf("") }
        var sueldo by remember { mutableStateOf("") }
        var errorMessage: String? by remember { mutableStateOf(null) }

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        OutlinedTextField(
                            label = { Text(text = "Nombre") },
                            value = nombre,
                            onValueChange = { nombre = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Sueldo") },
                            value = sueldo,
                            onValueChange = { sueldo = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        errorMessage?.let {
                            Text(text = it, color = Color.Red)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "new button"
                                )
                                Text(text = "Nuevo")
                            }
                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    if (nombre.isBlank())
                                        errorMessage = "Nombre vacio"

                                    scope.launch {
                                        saveTecnico(
                                            TecnicosEntity(
                                                nombre  = nombre,
                                                sueldo = sueldo
                                            )
                                        )
                                        nombre = ""
                                        sueldo = ""
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "save button"
                                )
                                Text(text = "Guardar")
                            }
                        }
                    }
                }

                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
                val tecnicosList by tecnicosDb.tecnicosDao().getAll()
                    .collectAsStateWithLifecycle(
                        initialValue = emptyList(),
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED
                    )
                TecnicosListScreen(tecnicosList)
            }
        }
    }

    @Composable
    fun TecnicosListScreen(tecnicosList: List<TecnicosEntity>) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Lista de tecnicos")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(tecnicosList) {
                    TecnicosRow(it)
                }
            }
        }
    }

    @Composable
    private fun TecnicosRow(it: TecnicosEntity) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = it.tecnicosId.toString())
            Text(
                modifier = Modifier.weight(2f),
                text = it.nombre,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(modifier = Modifier.weight(2f), text = it.sueldo)
        }
        HorizontalDivider()
    }

    private suspend fun saveTecnico(Tecnico: TecnicosEntity) {
        tecnicosDb.tecnicosDao().save(Tecnico)
    }
    @Entity(tableName = "Tecnicos")
    data class TecnicosEntity(
        @PrimaryKey
        val tecnicosId: Int? = null,
        val nombre: String = "",
        val sueldo: String = ""
    )


    @Dao
    interface tecnicosDao {
        @Upsert()
        suspend fun save(Tecnico: TecnicosEntity)

        @Query(
            """
        SELECT * 
        FROM Tecnicos 
        WHERE tecnicosId=:id  
        LIMIT 1
        """
        )
        suspend fun find(id: Int): TecnicosEntity?

        @Delete
        suspend fun delete(Tecnico: TecnicosEntity)

        @Query("SELECT * FROM Tecnicos")
        fun getAll(): Flow<List<TecnicosEntity>>
    }

    @Database(
        entities = [
            TecnicosEntity::class
        ],
        version = 1,
        exportSchema = false
    )
    abstract class TecnicosDb : RoomDatabase() {
        abstract fun tecnicosDao(): tecnicosDao
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Preview() {
        Registro_TecnicosTheme {
            val tecnicosList = listOf(
                TecnicosEntity(1, "Carlos Manuel", "200,000"),
                TecnicosEntity(2, "Alma Bet.", "200,000"),
            )
            TecnicosListScreen(tecnicosList)
        }
    }
}