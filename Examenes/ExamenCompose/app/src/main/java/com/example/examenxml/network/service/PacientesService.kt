package com.example.examenxml.network.service

import com.example.examenxml.domain.modelo.Paciente
import retrofit2.Response
import retrofit2.http.GET

interface PacientesService {
    @GET("/pacientes")
    suspend fun getAllPacientes(): Response<List<Paciente>>
}