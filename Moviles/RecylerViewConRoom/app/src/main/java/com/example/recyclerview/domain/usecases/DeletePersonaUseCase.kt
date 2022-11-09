package com.example.recyclerview.domain.usecases

import com.example.recyclerview.data.Repository
import com.example.recyclerview.domain.modelo.Persona

class DeletePersonaUseCase (private val repository: Repository) {
    suspend fun invoke(persona: Persona) = repository.deletePersona(persona)
}