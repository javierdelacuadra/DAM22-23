package com.example.formulario.domain.usecases

import com.example.formulario.data.Repository
import com.example.formulario.domain.modelo.Persona

class GetPersonasUseCase {
    operator fun invoke(): List<Persona> =
        Repository.getPersonas()
}