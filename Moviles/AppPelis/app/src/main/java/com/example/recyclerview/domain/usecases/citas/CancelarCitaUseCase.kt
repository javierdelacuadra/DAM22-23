package com.example.recyclerview.domain.usecases.citas

import com.example.recyclerview.data.repositories.CitasRepository
import com.example.recyclerview.domain.modelo.Cita
import javax.inject.Inject

class CancelarCitaUseCase @Inject constructor(private val repository: CitasRepository) {
    suspend fun invoke(cita: Cita) = repository.deleteCita(cita)
}