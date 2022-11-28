package com.example.recyclerview.domain.usecases.componentes

import com.example.recyclerview.data.EquiposRepository
import javax.inject.Inject

class GetComponentesUseCase @Inject constructor(private val repository: EquiposRepository) {
    suspend fun invoke(nombreEquipo: String) = repository.getComponentes(nombreEquipo)
}