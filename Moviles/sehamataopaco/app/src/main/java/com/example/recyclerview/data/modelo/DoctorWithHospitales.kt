package com.example.recyclerview.data.modelo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DoctorWithHospitales(
    @Embedded val doctor: DoctorEntity,
    @Relation(
        parentColumn = "email",
        entityColumn = "nombre",
        associateBy = Junction(HospitalAndDoctorEntity::class)
    )
    val hospitales: List<HospitalEntity>,
)
