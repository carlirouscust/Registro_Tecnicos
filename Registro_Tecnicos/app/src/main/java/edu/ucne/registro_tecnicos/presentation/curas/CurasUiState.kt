package edu.ucne.registro_tecnicos.presentation.curas

import edu.ucne.registro_tecnicos.presentation.remote.dto.curasDto

data class CurasUiState(
    val curasId: Int? = null,
    val descripcion: String? = null,
    val monto: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val curas: List<curasDto> = emptyList(),
    val inputError: String? = null
)