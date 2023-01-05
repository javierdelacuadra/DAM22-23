package com.example.recyclerview.domain.usecases.tarjetas

import com.example.recyclerview.data.repositories.Repository
import com.example.recyclerview.domain.modelo.Tarjeta
import javax.inject.Inject

class AddTarjetaUseCase @Inject constructor(val repository: Repository) {

    suspend fun invoke(tarjeta: Tarjeta) = repository.addTarjeta(tarjeta)
}