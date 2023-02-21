package com.example.examenxml.data.repositories

import com.example.examenxml.data.dao.PacientesDao
import com.example.examenxml.data.modelo.*
import com.example.examenxml.data.remote.PacientesDataSource
import com.example.examenxml.domain.modelo.Paciente
import com.example.examenxml.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
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
                saveOnCache(result)
            }

        }.flowOn(Dispatchers.IO)
    }

    private fun saveOnCache(result: NetworkResult<List<Paciente>>) {
        result.data?.let { it ->
            pacienteDao.deleteAll(it.map { it.toPacienteEntity() })
            pacienteDao.insertAll(it.map { it.toPacienteEntity() })
            it.forEach { paciente ->
                pacienteDao.insertAllEnfermedades(paciente.enfermedades.map {
                    it.toEnfermedadEntity(
                        paciente.id
                    )
                })
            }
        }
    }

    private fun fetchAllPacientesCached(): NetworkResult<List<Paciente>> =
        pacienteDao.getAll().let { list ->
            NetworkResult.Success(list.map { it.toPaciente() })
        }

    fun fetchPacienteByID(id: String): Flow<NetworkResult<PacienteWithEnfermedades>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = pacienteDao.getPacienteByIdWithEnfermedades(UUID.fromString(id))
            emit(NetworkResult.Success(result))
        }.flowOn(Dispatchers.IO)
    }

    fun addEnfermedad(enfermedadEntity: EnfermedadEntity): Flow<NetworkResult<EnfermedadEntity>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = pacienteDataSource.addEnfermedad(enfermedadEntity)
            emit(result)
            if (result is NetworkResult.Success) {
                pacienteDao.insertEnfermedad(enfermedadEntity)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun editarPaciente(id: String, nombre: String): Flow<NetworkResult<Paciente>> {
        return flow {
            emit(NetworkResult.Loading())
            val paciente = pacienteDao.getPacienteById(UUID.fromString(id))
            paciente.nombre = nombre
            val result = pacienteDataSource.editarPaciente(id, paciente.toPaciente())
            emit(result)
            if (result is NetworkResult.Success) {
                pacienteDao.updatePaciente(UUID.fromString(id), nombre)
            }
        }.flowOn(Dispatchers.IO)
    }
}