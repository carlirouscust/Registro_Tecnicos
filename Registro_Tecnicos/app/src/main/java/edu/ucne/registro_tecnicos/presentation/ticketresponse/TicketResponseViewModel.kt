package edu.ucne.registro_tecnicos.presentation.ticketresponse

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.registro_tecnicos.data.local.entities.TicketResponseEntity
import edu.ucne.registro_tecnicos.data.repository.TicketResponseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

class TicketResponseViewModel(
    private val repository: TicketResponseRepository
) : ViewModel() {

    private val _responses = MutableStateFlow<List<TicketResponseEntity>>(emptyList())
    val responses: StateFlow<List<TicketResponseEntity>> = _responses.asStateFlow()

    fun loadResponses(ticketId: Int) {
        viewModelScope.launch {
            repository.getResponsesByTicket(ticketId).collectLatest {
                _responses.value = it
            }
        }
    }

    fun sendResponse(ticketId: Int, nombre: String, mensaje: String, tipoUsuario: String) {
        val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val response = TicketResponseEntity(
            ticketId = ticketId,
            nombre = nombre,
            mensaje = mensaje,
            fecha = fecha,
            tipoUsuario = tipoUsuario
        )
        viewModelScope.launch {
            repository.insert(response)
        }
    }
}