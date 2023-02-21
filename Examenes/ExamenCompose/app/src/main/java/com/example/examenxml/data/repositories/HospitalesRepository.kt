package com.example.examenxml.data.repositories

import com.example.examenxml.data.remote.HospitalesDataSource
import com.example.examenxml.domain.modelo.Hospital
import com.example.examenxml.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HospitalesRepository @Inject constructor(
    private val hospitalDataSource: HospitalesDataSource,
) {
    fun fetchHospitales(): Flow<NetworkResult<List<Hospital>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = hospitalDataSource.fetchHospitales()
            emit(result)
            if (result is NetworkResult.Success) {
                result.data?.let { _ ->
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}