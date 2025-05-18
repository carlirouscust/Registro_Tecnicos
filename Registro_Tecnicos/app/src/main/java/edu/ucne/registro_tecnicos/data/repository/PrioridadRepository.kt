package edu.ucne.registro_tecnicos.data.repository

import edu.ucne.registro_tecnicos.data.local.dao.PrioridadDao
import edu.ucne.registro_tecnicos.data.local.entities.PrioridadEntity

class PrioridadRepository(private val dao: PrioridadDao)  {
    suspend fun obtenerPrioridades(): List<PrioridadEntity> {
        return dao.getPrioridades()
    }

    suspend fun insertarPrioridades(prioridades: List<PrioridadEntity>) {
        dao.insertarPrioridades(prioridades)
    }
}