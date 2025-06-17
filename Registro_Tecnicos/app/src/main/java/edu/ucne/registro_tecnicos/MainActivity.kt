package edu.ucne.registro_tecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registro_tecnicos.data.local.database.TecnicosDb
import edu.ucne.registro_tecnicos.data.local.repository.TecnicosRepository
import edu.ucne.registro_tecnicos.data.local.repository.TicketRepository
import edu.ucne.registro_tecnicos.data.local.repository.TicketResponseRepository
import edu.ucne.registro_tecnicos.data.repository.TecnicosNavHost
import edu.ucne.registro_tecnicos.presentation.curas.CurasViewModel
import edu.ucne.registro_tecnicos.presentation.tecnicos.TecnicosViewModel
import edu.ucne.registro_tecnicos.presentation.ticket.TicketViewModel
import edu.ucne.registro_tecnicos.presentation.ticketresponse.TicketResponseViewModel
import edu.ucne.registro_tecnicos.ui.theme.Registro_TecnicosTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Registro_TecnicosTheme {
                val navController = rememberNavController()
                val TecnicosViewModel: TecnicosViewModel = hiltViewModel()
                val TicketViewModel: TicketViewModel = hiltViewModel()
                val CurasViewModel: CurasViewModel = hiltViewModel()
                val TicketResponseViewModel: TicketResponseViewModel = hiltViewModel()

                TecnicosNavHost(
                    navHostController = navController,
                    tecnicoViewModel = TecnicosViewModel,
                    ticketViewModel = TicketViewModel,
                    ticketResponseViewModel = TicketResponseViewModel,
                    curasViewModel = CurasViewModel
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Registro_TecnicosTheme {
        Greeting("Android")
    }
}