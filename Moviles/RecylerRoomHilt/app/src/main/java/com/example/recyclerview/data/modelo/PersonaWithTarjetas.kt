package com.example.recyclerview.data.modelo


import androidx.room.Embedded
import androidx.room.Relation
import com.example.recyclerview.data.common.Constantes

data class PersonaWithTarjetas(
    @Embedded val persona: PersonaEntity,
    @Relation(
        parentColumn = Constantes.EMAIL,
        entityColumn = Constantes.EMAIL
    )
    val tarjeta: List<TarjetaEntity>?
)