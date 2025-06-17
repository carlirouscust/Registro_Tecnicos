package edu.ucne.registro_tecnicos.data.local.repository

import edu.ucne.registro_tecnicos.data.local.dao.TecnicosDao
import edu.ucne.registro_tecnicos.data.local.entities.TecnicosEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TecnicosRepository @Inject constructor (private val dao: TecnicosDao) {

    suspend fun save(tecnico: TecnicosEntity) = dao.save(tecnico)

    suspend fun find(id: Int): TecnicosEntity? = dao.find(id)

    suspend fun delete(tecnico: TecnicosEntity) = dao.delete(tecnico)

    fun getAll(): Flow<List<TecnicosEntity>> = dao.getAll()
}