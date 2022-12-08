package com.example.recyclerview.domain.usecases.equipos

import com.example.recyclerview.data.EquiposRepository
import com.example.recyclerview.domain.modelo.Equipo
import javax.inject.Inject

class AddEquipoUseCase @Inject constructor(private val repository: EquiposRepository) {
    suspend fun invoke(equipo: Equipo) = repository.addEquipo(equipo)
}