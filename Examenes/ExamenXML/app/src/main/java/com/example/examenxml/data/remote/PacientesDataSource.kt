package com.example.examenxml.data.remote

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
}