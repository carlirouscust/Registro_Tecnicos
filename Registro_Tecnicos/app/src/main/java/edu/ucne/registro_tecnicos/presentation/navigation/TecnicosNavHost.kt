package edu.ucne.registro_tecnicos.data.repository

import Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registro_tecnicos.data.local.entities.PrioridadEntity
import edu.ucne.registro_tecnicos.presentation.tecnicos.TecnicoListScreen
import edu.ucne.registro_tecnicos.presentation.tecnicos.TecnicoScreen
import edu.ucne.registro_tecnicos.presentation.tecnicos.TecnicosViewModel
import edu.ucne.registro_tecnicos.presentation.navigation.Screen
import edu.ucne.registro_tecnicos.presentation.ticket.TicketListScreen
import edu.ucne.registro_tecnicos.presentation.ticket.TicketScreen
import edu.ucne.registro_tecnicos.presentation.ticket.TicketViewModel

@Composable
fun TecnicosNavHost(
    navHostController: NavHostController,
    tecnicoViewModel: TecnicosViewModel,
    ticketViewModel: TicketViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = "Home"
    ) {
        composable("Home") {
            Home(
                goToTecnico = {
                    navHostController.navigate("TecnicoList")
                },
                goToTicket = {
                    navHostController.navigate("TicketList")
                }
            )
        }

        // Lista de técnicos
        composable("TecnicoList") {
            val tecnicoList = tecnicoViewModel.tecnicoList.collectAsState().value

            TecnicoListScreen(
                tecnicoList = tecnicoList,
                onEdit = { tecnico ->
                    navHostController.navigate("Tecnico/${tecnico.tecnicosId}")
                },
                onCreate = {
                    navHostController.navigate("Tecnico/null")
                },
                onDelete = { tecnico ->
                    tecnicoViewModel.delete(tecnico)
                }
            )
        }

        composable("TicketList") {
            val ticketList = ticketViewModel.ticketList.collectAsState().value
            val tecnicoList = tecnicoViewModel.tecnicoList.collectAsState().value

            TicketListScreen(
                navController = navHostController, // <- Este es el parámetro nuevo
                ticketList = ticketList,
                tecnicos = tecnicoList,
                onDelete = { ticket ->
                    ticketViewModel.delete(ticket)
                }
            )
        }


        // Crear o editar técnico
        composable("Tecnico/{tecnicoId}") { backStackEntry ->
            val tecnicoIdParam = backStackEntry.arguments?.getString("tecnicoId")
            val tecnicoId = if (tecnicoIdParam == "null") null else tecnicoIdParam?.toIntOrNull()
            val tecnico = tecnicoViewModel.getTecnicoById(tecnicoId)



            TecnicoScreen(
                tecnico = tecnico,
                agregarTecnico = { nombre, sueldo ->
                    if (tecnico == null) {
                        tecnicoViewModel.agregarTecnico(nombre, sueldo)
                    } else {
                        tecnicoViewModel.update(tecnico.copy(nombre = nombre, sueldo = sueldo))
                    }
                    navHostController.popBackStack()
                },
                onCancel = {
                    navHostController.popBackStack()
                }
            )
        }

        composable("Ticket/{ticketId}") { backStackEntry ->
            val ticketIdParam = backStackEntry.arguments?.getString("ticketId")
            val ticketId = if (ticketIdParam == "null") null else ticketIdParam?.toIntOrNull()
            val ticket = ticketViewModel.getTicketById(ticketId)
            val tecnicos = tecnicoViewModel.tecnicoList.collectAsState().value


            val prioridades = listOf(
                PrioridadEntity(1, "Baja"),
                PrioridadEntity(2, "Media"),
                PrioridadEntity(3, "Alta")
            )


            TicketScreen(
                ticket = ticket,
                prioridades = prioridades,
                tecnicos = tecnicos,
                agregarTicket = { fecha, prioridadId, cliente, asunto, descripcion, tecnicoId ->
                    if (ticket == null) {
                        ticketViewModel.agregarTicket(fecha, prioridadId, cliente, asunto, descripcion, tecnicoId)
                    } else {
                        ticketViewModel.update(
                            ticket.copy(
                                fecha = fecha,
                                prioridadId = prioridadId,
                                cliente = cliente,
                                asunto = asunto,
                                descripcion = descripcion,
                                tecnicoId = tecnicoId
                            )
                        )
                    }
                    navHostController.popBackStack()
                },
                onCancel = { navHostController.popBackStack() }
            )

        }

    }
}
