package com.example.recyclerview.domain.usecases

import com.example.recyclerview.data.Repository
import com.example.recyclerview.domain.modelo.Persona

class AddPersonaUseCase {
    operator fun invoke(persona: Persona) =
        Repository.addPersona(persona)
}