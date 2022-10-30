package com.example.recyclerview.domain.usecases

import com.example.recyclerview.data.Repository
import com.example.recyclerview.domain.modelo.Persona

class UpdatePersonaUseCase {
    operator fun invoke(persona : Persona): Boolean {
        return Repository.updatePersona(persona)
    }
}