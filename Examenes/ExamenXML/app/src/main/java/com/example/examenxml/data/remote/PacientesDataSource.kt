package com.example.examenxml.data.remote

import com.example.examenxml.data.modelo.EnfermedadEntity
import com.example.examenxml.domain.modelo.Paciente
import com.example.examenxml.network.NetworkResult
import com.example.examenxml.network.service.PacientesService
import javax.inject.Inject

class PacientesDataSource @Inject constructor(private val pacientesService: PacientesService) :
    BaseApiResponse() {
    suspend fun fetchAllPacientes(): NetworkResult<List<Paciente>> {
        return safeApiCall(apiCall = { pacientesService.getAllPacientes() },
            transform = { hospitalesResponse ->
                hospitalesResponse.map { it }
            })
    }

    suspend fun addEnfermedad(enfermedadEntity: EnfermedadEntity): NetworkResult<EnfermedadEntity> {
        return safeApiCall(apiCall = { pacientesService.addEnfermedad(enfermedadEntity.pacienteId.toString(), enfermedadEntity) },
            transform = { hospitalesResponse ->
                hospitalesResponse
            })
    }

    suspend fun editarPaciente(id: String, paciente: Paciente): NetworkResult<Paciente> {
        return safeApiCall(apiCall = { pacientesService.editarPaciente(id, paciente) },
            transform = { hospitalesResponse ->
                hospitalesResponse
            })
    }
}