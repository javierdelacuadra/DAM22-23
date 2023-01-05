package com.example.recyclerview.data.modelo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class HospitalWithDoctores(
    @Embedded val hospital: HospitalEntity,
    @Relation(
        parentColumn = "nombre",
        entityColumn = "email",
        associateBy = Junction(HospitalAndDoctorEntity::class)
    )
    val doctores: List<DoctorEntity>,
)
