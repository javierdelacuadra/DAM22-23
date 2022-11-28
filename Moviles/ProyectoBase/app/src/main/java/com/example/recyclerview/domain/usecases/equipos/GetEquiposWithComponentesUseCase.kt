package com.example.recyclerview.domain.usecases.equipos

import com.example.recyclerview.data.EquiposRepository
import javax.inject.Inject

class GetEquiposWithComponentesUseCase @Inject constructor(val repository: EquiposRepository) {

    suspend fun invoke() = repository.getEquiposWithComponentes()
}