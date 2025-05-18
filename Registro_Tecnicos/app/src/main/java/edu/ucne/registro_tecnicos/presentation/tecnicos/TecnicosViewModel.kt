package edu.ucne.registro_tecnicos.presentation.tecnicos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import edu.ucne.registro_tecnicos.data.local.entities.TecnicosEntity
import edu.ucne.registro_tecnicos.data.repository.TecnicosRepository
import kotlinx.coroutines.flow.stateIn

class TecnicosViewModel(
    private val repository: TecnicosRepository
) : ViewModel() {

    val tecnicoList: StateFlow<List<TecnicosEntity>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarTecnico(nombre: String, sueldo: Double) {
        val tecnico = TecnicosEntity(
            nombre = nombre,
            sueldo = sueldo
        )
        saveTecnico(tecnico)
    }

    fun saveTecnico(tecnico: TecnicosEntity) {
        viewModelScope.launch {
            repository.save(tecnico)
        }
    }

    fun delete(tecnico: TecnicosEntity) {
        viewModelScope.launch {
            repository.delete(tecnico)
        }
    }

    fun update(tecnico: TecnicosEntity) {
        saveTecnico(tecnico)
    }

    fun getTecnicoById(id: Int?): TecnicosEntity? {
        return tecnicoList.value.find { it.tecnicosId == id }
    }
}