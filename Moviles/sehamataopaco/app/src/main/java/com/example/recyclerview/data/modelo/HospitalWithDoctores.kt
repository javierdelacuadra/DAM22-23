package com.example.recyclerview.data.modelo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recyclerview.data.common.Constantes

data class HospitalWithDoctores(
    @Embedded val hospital: HospitalEntity,
    @Relation(
        parentColumn = Constantes.NOMBRE,
        entityColumn = Constantes.EMAIL,
        associateBy = Junction(HospitalAndDoctorEntity::class)
    )
    val doctores: List<DoctorEntity>,
)
