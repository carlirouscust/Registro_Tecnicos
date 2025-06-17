package edu.ucne.registro_tecnicos.presentation.remote

import edu.ucne.registro_tecnicos.presentation.remote.dto.curasDto
import javax.inject.Inject

class DataSource @Inject constructor(
    private val api: CurasApi
){
    suspend fun getCuras(): List<curasDto> = api.getCuras()

    suspend fun createCuras(curas: curasDto): curasDto =
        api.createCuras(curas)

    suspend fun getCuras(id: Int): curasDto =api.getCuras(id)

    suspend fun updateCuras(id: Int, curas: curasDto): curasDto =
        api.updateCuras(id, curas)

    suspend fun deleteCuras(id: Int) = api.deleteCuras(id)
}