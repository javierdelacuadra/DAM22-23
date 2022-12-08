package com.example.recyclerview.domain.usecases.equipos

import com.example.recyclerview.data.EquiposRepository
import javax.inject.Inject

class DeleteEquipoUseCase @Inject constructor(private val repository: EquiposRepository) {
    suspend fun invoke(nombreEquipo: String) = repository.deleteEquipo(nombreEquipo)
}