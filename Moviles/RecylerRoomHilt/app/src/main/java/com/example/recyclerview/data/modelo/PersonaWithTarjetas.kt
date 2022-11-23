package com.example.recyclerview.data.modelo


import androidx.room.Embedded
import androidx.room.Relation

data class PersonaWithTarjetas(
    @Embedded val persona: PersonaEntity,
    @Relation(
        parentColumn = "email",
        entityColumn = "email"
    )
    val tarjeta: List<TarjetaEntity>?
)