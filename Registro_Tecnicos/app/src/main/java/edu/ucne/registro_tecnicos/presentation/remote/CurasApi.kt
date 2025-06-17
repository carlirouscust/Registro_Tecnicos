package edu.ucne.registro_tecnicos.presentation.remote

import edu.ucne.registro_tecnicos.presentation.remote.dto.curasDto
import retrofit2.http.*

interface CurasApi {
    @GET("api/Curas")
    suspend fun getCuras(): List<curasDto>

    @GET("api/Curas/{id}")
    suspend fun getCuras(@Path("id") id: Int): curasDto

    @POST("api/Curas")
    suspend fun createCuras(@Body curasDto: curasDto): curasDto

    @PUT("api/Curas/{id}")
    suspend fun updateCuras(@Path("id") id: Int, @Body curasDto: curasDto): curasDto

    @DELETE("api/Curas/{id}")
    suspend fun deleteCuras(@Path("id") id: Int)
}