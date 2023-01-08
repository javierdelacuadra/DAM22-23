package com.example.recyclerview.data.modelo

import androidx.room.Embedded
import androidx.room.Relation
import com.example.recyclerview.data.common.Constantes

data class DoctorWithCitas(
    @Embedded val doctor: DoctorEntity,
    @Relation(
        parentColumn = Constantes.EMAIL,
        entityColumn = Constantes.EMAIL_DOCTOR
    )
    val citas: List<CitaEntity>,
)
