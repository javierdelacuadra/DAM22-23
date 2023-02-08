package com.example.formulariocompose.domain.usecases

import com.example.formulariocompose.data.Repository
import com.example.formulariocompose.domain.modelo.Persona
import javax.inject.Inject

class DeletePersonaUseCase @Inject constructor(private val repository: Repository) {
    suspend fun invoke(persona: Persona) = repository.deletePersona(persona)
}