package com.example.recyclerview.data.modelo

import com.example.recyclerview.domain.modelo.Componente
import com.example.recyclerview.domain.modelo.Equipo
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

fun EquipoWithComponentes.toEquipo(): Equipo {
    return Equipo(
        this.equipo.nombre,
        this.equipo.nacionalidad,
        this.equipo.puesto,
        this.componentes.map { it.toComponente() },
    )
}

fun EquipoEntity.toEquipo(): Equipo {
    return Equipo(
        this.nombre,
        this.nacionalidad,
        this.puesto,
    )
}

fun Equipo.toEquipoEntity() : EquipoEntity {
    return EquipoEntity(
        this.nombre,
        this.nacionalidad,
        this.puesto
    )
}

fun ComponenteEntity.toComponente(): Componente {
    return Componente(
        this.id,
        this.nombreJugador,
        this.nombreEquipo,
        this.tipo
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