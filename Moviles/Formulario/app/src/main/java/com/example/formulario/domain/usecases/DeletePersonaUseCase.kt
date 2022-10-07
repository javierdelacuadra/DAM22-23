package com.example.formulario.domain.usecases

import com.example.formulario.data.Repository
import com.example.formulario.domain.modelo.Persona

class DeletePersonaUseCase {
    operator fun invoke(persona: Persona) =
        Repository.deletePersona(persona)
}