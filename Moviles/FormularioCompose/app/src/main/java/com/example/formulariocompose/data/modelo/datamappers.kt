package com.example.recyclerview.data.modelo

import com.example.recyclerview.domain.modelo.Persona

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