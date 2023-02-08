package com.example.formulariocompose.data.modelo

import com.example.formulariocompose.domain.modelo.Persona

fun PersonaEntity.toPersona(): Persona {
    return Persona(
        this.nombre,
        this.password,
        this.email,
    )
}

fun Persona.toPersonaEntity(): PersonaEntity {
    return PersonaEntity(
        this.nombre,
        this.password,
        this.email,
    )
}