package com.example.recyclerview.domain.usecases.equipos

import com.example.recyclerview.data.EquiposRepository
import javax.inject.Inject

class GetEquiposUseCase @Inject constructor(private val repository: EquiposRepository) {
    suspend fun invoke() = repository.getEquipos()
}