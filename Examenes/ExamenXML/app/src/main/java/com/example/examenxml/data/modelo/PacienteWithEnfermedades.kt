package com.example.examenxml.data.modelo

import androidx.room.Embedded
import androidx.room.Relation

data class PacienteWithEnfermedades(
    @Embedded
    val paciente: PacienteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "pacienteId"
    )
    var enfermedades: MutableList<EnfermedadEntity>
)
