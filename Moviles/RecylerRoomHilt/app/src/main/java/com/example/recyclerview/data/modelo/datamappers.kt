package com.example.recyclerview.data.modelo

import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.modelo.Tarjeta

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

fun TarjetaEntity.toTarjeta(): Tarjeta {
    return Tarjeta(
        this.numeroTarjeta,
        this.fechaCaducidad,
        this.cvv,
        this.email,
    )
}

fun Tarjeta.toTarjetaEntity(): TarjetaEntity {
    return TarjetaEntity(
        this.numeroTarjeta,
        this.fechaCaducidad,
        this.cvv,
        this.email,
    )
}

fun PersonaWithTarjetas.toPersona(): Persona {
    return Persona(
        this.persona.nombre,
        this.persona.password,
        this.persona.email,
        this.tarjeta?.map { it.toTarjeta() },
    )
}