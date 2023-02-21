package com.example.examenxml.network.service

import com.example.examenxml.data.modelo.EnfermedadEntity
import com.example.examenxml.domain.modelo.Paciente
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PacientesService {
    @GET("/pacientes")
    suspend fun getAllPacientes(): Response<List<Paciente>>

    @POST("/pacientes/{id}")
    suspend fun addEnfermedad(@Path("id") id: String, @Body enfermedadEntity: EnfermedadEntity): Response<EnfermedadEntity>

    @PUT("/pacientes/{id}")
    suspend fun editarPaciente(@Path("id") id: String, @Body paciente: Paciente): Response<Paciente>
}