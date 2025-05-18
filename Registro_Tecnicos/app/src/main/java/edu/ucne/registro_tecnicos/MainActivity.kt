package edu.ucne.registro_tecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.ucne.registro_tecnicos.data.local.database.TecnicosDb
import edu.ucne.registro_tecnicos.data.repository.TecnicosRepository
import edu.ucne.registro_tecnicos.data.repository.TicketRepository
import edu.ucne.registro_tecnicos.data.repository.TecnicosNavHost
import edu.ucne.registro_tecnicos.presentation.tecnicos.TecnicosViewModel
import edu.ucne.registro_tecnicos.presentation.ticket.TicketViewModel
import edu.ucne.registro_tecnicos.ui.theme.Registro_TecnicosTheme

class MainActivity : ComponentActivity() {
    private lateinit var tecnicoDb: TecnicosDb
    private lateinit var tecnicosRepository: TecnicosRepository
    private lateinit var tecnicosViewModel: TecnicosViewModel

    private lateinit var ticketRepository: TicketRepository
    private lateinit var ticketViewModel: TicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialización de la base de datos
        tecnicoDb = Room.databaseBuilder(
            applicationContext,
            TecnicosDb::class.java,
            "Tecnico.db"
        ).fallbackToDestructiveMigration()
            .build()

        // Repositorios y ViewModels
        tecnicosRepository = TecnicosRepository(tecnicoDb.tecnicosDao())
        tecnicosViewModel = TecnicosViewModel(tecnicosRepository)

        ticketRepository = TicketRepository(tecnicoDb)
        ticketViewModel = TicketViewModel(ticketRepository)

        setContent {
            Registro_TecnicosTheme {
                val navController = rememberNavController()
                TecnicosNavHost(
                    navHostController = navController,
                    tecnicoViewModel = tecnicosViewModel,
                    ticketViewModel = ticketViewModel
                )
            }
        }
    }
}
