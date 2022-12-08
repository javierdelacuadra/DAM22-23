package com.example.recyclerview.data.modelo

import androidx.room.Embedded
import androidx.room.Relation

data class EquipoWithComponentes(
    @Embedded val equipo: EquipoEntity,
    @Relation(
        parentColumn = "nombre",
        entityColumn = "nombreEquipo"
    )
    val componentes: List<ComponenteEntity>
)
