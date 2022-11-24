package com.example.recyclerview.domain.usecases.tarjetas

import com.example.recyclerview.data.Repository
import javax.inject.Inject

class GetPersonasWithTarjetasUseCase @Inject constructor(val repository: Repository) {

    suspend fun invoke() = repository.getPersonasWithTarjetas()
}