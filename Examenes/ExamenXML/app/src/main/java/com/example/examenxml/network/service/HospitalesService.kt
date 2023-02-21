package com.example.examenxml.network.service

import com.example.examenxml.domain.modelo.Hospital
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET

interface HospitalesService {
    @GET("/hospitales")
    suspend fun getHospitales(): Response<List<Hospital>>

    @DELETE("/hospitales/{id}")
    suspend fun deleteHospital(id: String)
}