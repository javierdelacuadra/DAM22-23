package com.example.recyclerview.domain.usecases.tarjetas

import com.example.recyclerview.data.repositories.Repository
import javax.inject.Inject

class GetTarjetasUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getTarjetas()
}