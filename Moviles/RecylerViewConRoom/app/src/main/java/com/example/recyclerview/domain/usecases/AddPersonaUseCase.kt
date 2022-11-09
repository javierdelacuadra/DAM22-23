package com.example.recyclerview.domain.usecases

import com.example.recyclerview.data.Repository
import com.example.recyclerview.domain.modelo.Persona

class AddPersonaUseCase (private val repository: Repository) {
    suspend fun invoke(persona: Persona) = repository.addPersona(persona)
}