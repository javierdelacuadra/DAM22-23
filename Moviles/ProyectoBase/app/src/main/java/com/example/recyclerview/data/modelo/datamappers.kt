package com.example.recyclerview.data.modelo

import com.example.recyclerview.domain.modelo.Componente
import com.example.recyclerview.domain.modelo.Equipo

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

fun Equipo.toEquipoEntity(): EquipoEntity {
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