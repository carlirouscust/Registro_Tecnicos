package edu.ucne.registro_tecnicos.presentation.tecnicos
import edu.ucne.registro_tecnicos.data.local.entities.TecnicosEntity

data class tecnicosUiState (
    val tecnicosId: Int? = null,
    val nombre: String = "",
    val sueldo: Double = 0.0,
    val tecnicos: List<TecnicosEntity> = emptyList()

)