package com.example.recyclerview.domain.usecases.personas

import com.example.recyclerview.data.Repository
import com.example.recyclerview.domain.modelo.Persona
import javax.inject.Inject

class DeletePersonaUseCase @Inject constructor(private val repository: Repository) {
    suspend fun invoke(persona: Persona) = repository.deletePersona(persona)
}