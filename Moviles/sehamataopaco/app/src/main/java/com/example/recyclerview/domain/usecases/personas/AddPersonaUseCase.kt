package com.example.recyclerview.domain.usecases.personas

import com.example.recyclerview.data.repositories.Repository
import com.example.recyclerview.domain.modelo.Persona
import javax.inject.Inject

class AddPersonaUseCase @Inject constructor(private val repository: Repository) {
    suspend fun invoke(persona: Persona) = repository.addPersona(persona)
}