package com.example.examenxml.data.repositories

import com.example.examenxml.data.dao.PacientesDao
import com.example.examenxml.data.modelo.toPaciente
import com.example.examenxml.data.modelo.toPacienteEntity
import com.example.examenxml.data.remote.PacientesDataSource
import com.example.examenxml.domain.modelo.Paciente
import com.example.examenxml.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PacientesRepository @Inject constructor(
    private val pacienteDataSource: PacientesDataSource,
    private val pacienteDao: PacientesDao
) {
    fun fetchAllPacientes(): Flow<NetworkResult<List<Paciente>>> {
        return flow {
            emit(fetchAllPacientesCached())
            emit(NetworkResult.Loading())
            val result = pacienteDataSource.fetchAllPacientes()
            emit(result)
            if (result is NetworkResult.Success) {
                result.data?.let { it ->
                    pacienteDao.deleteAll(it.map { it.toPacienteEntity() })
                    pacienteDao.insertAll(it.map { it.toPacienteEntity() })
                }
            }

        }.flowOn(Dispatchers.IO)
    }

    private fun fetchAllPacientesCached(): NetworkResult<List<Paciente>> =
        pacienteDao.getAll().let { list ->
            NetworkResult.Success(list.map { it.toPaciente() })
        }

    fun fetchPacienteByID(id: String): Flow<NetworkResult<Paciente>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = pacienteDao.getPacienteById(id).toPaciente()
            emit(NetworkResult.Success(result))
        }.flowOn(Dispatchers.IO)
    }
}