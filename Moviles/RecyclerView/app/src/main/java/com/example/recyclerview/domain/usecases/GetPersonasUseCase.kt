package com.example.recyclerview.domain.usecases

import com.example.recyclerview.data.Repository
import com.example.recyclerview.domain.modelo.Persona

class GetPersonasUseCase {
    operator fun invoke(): List<Persona> =
        Repository.getPersonas()
}