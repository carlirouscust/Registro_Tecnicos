package edu.ucne.registro_tecnicos.presentation.curas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_tecnicos.presentation.remote.dto.curasDto
import edu.ucne.registro_tecnicos.presentation.remote.Resource
import edu.ucne.registro_tecnicos.data.local.repository.CurasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurasViewModel @Inject constructor(
    private val repository: CurasRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurasUiState())
    val uiState: StateFlow<CurasUiState> = _uiState.asStateFlow()

    init {
        getCuras()
    }

    private fun getCuras() {
        viewModelScope.launch {
            repository.getCuras().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            curas = result.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun saveCura(cura: curasDto) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                if (cura.curasId == null) {
                    repository.createCuras(cura.copy(curasId = null)) // asegúrate que siempre vaya como null
                } else {
                    repository.updateCuras(cura)
                }
                getCuras()
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error guardando la cura: ${e.localizedMessage}"
                )
            }
        }
    }

    fun agregarCura(descripcion: String, monto: Int) {
        if (descripcion.isBlank()) {
            setInputError("La descripción es requerida")
            return
        }
        if (monto <= 0.0) {
            setInputError("El monto debe ser mayor que 0")
            return
        }
        clearInputError()
        saveCura(curasDto(curasId = null, descripcion = descripcion, monto = monto))
    }

    fun updateCura(cura: curasDto) {
        if (cura.descripcion.isBlank()) {
            setInputError("La descripción es requerida")
            return
        }
        if (cura.monto <= 0.0) {
            setInputError("El monto debe ser mayor que 0")
            return
        }
        clearInputError()
        saveCura(cura)
    }

    fun deleteCura(id: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                repository.deleteCuras(id)
                getCuras()
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error eliminando la cura: ${e.localizedMessage}"
                )
            }
        }
    }

    fun getCuraById(id: Int?): curasDto? {
        return _uiState.value.curas.find { it.curasId == id }
    }

    private fun setInputError(message: String) {
        _uiState.value = _uiState.value.copy(inputError = message)
    }

    private fun clearInputError() {
        _uiState.value = _uiState.value.copy(inputError = null)
    }
}