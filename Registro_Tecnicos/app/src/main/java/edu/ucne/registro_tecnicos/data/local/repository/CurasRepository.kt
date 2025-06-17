package edu.ucne.registro_tecnicos.data.local.repository

import androidx.compose.material3.DatePicker
import edu.ucne.registro_tecnicos.presentation.remote.dto.curasDto
import edu.ucne.registro_tecnicos.presentation.remote.Resource
import edu.ucne.registro_tecnicos.presentation.remote.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurasRepository @Inject constructor(
    private val dataSource: DataSource
) {
    fun getCuras(): Flow<Resource<List<curasDto>>> = flow {
        try {
            emit(Resource.Loading())
            val curas = dataSource.getCuras()
            emit(Resource.Success(curas))
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }


    suspend fun createCuras (curasDto: curasDto) =
        dataSource.createCuras(curasDto)

    suspend fun updateCuras(curasDto: curasDto) =
        dataSource.updateCuras(curasDto.curasId ?: 0, curasDto)

    suspend fun deleteCuras(id: Int) =
        dataSource.deleteCuras(id)
}