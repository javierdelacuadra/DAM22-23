package com.example.recyclerview.data.modelo

import androidx.room.Embedded
import androidx.room.Relation

data class DoctorWithCitas(
    @Embedded val doctor: DoctorEntity,
    @Relation(
        parentColumn = "email",
        entityColumn = "emailDoctor"
    )
    val citas: List<CitaEntity>,
)
