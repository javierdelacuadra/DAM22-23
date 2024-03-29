package com.example.recyclerview.domain.usecases

import com.example.recyclerview.data.Repository
import com.example.recyclerview.domain.modelo.Persona

class DeletePersonaUseCase {
    operator fun invoke(email: String) =
        Repository.deletePersona(email)
}