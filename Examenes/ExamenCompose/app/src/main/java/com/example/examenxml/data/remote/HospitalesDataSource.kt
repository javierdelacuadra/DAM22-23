package com.example.examenxml.data.remote

import com.example.examenxml.domain.modelo.Hospital
import com.example.examenxml.network.NetworkResult
import com.example.examenxml.network.service.HospitalesService
import javax.inject.Inject

class HospitalesDataSource @Inject constructor(private val hospitalesService: HospitalesService) :
    BaseApiResponse() {

    suspend fun fetchHospitales(): NetworkResult<List<Hospital>> {
        return safeApiCall(apiCall = { hospitalesService.getHospitales() },
            transform = { hospitalesResponse ->
                hospitalesResponse.map { it }
            })
    }

    suspend fun deleteHospital(id: String) {
        hospitalesService.deleteHospital(id)
    }
}