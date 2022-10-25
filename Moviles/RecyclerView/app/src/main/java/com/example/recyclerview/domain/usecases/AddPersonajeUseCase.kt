package com.example.recyclerview.domain.usecases

import com.example.recyclerview.data.RepositoryPersonaje
import com.example.recyclerview.domain.modelo.Personaje

class AddPersonajeUseCase {
    operator fun invoke(personaje: Personaje) =
        RepositoryPersonaje().addPersonaje(personaje)
}