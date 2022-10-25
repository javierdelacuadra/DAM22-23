package com.example.recyclerview.domain.usecases

import com.example.recyclerview.data.Repository
import com.example.recyclerview.domain.modelo.Persona

class DeletePersonaUseCase {
    operator fun invoke(persona: Persona) =
        Repository.deletePersona(persona)
}