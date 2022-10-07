package com.example.formulario.domain.usecases

import com.example.formulario.data.Repository

class GetPersonasUseCase {
    operator fun invoke() =
        Repository.getPersonas()
}