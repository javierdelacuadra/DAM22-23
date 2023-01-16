package com.example.recyclerview.data.modelo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recyclerview.data.common.Constantes

data class DoctorWithHospitales(
    @Embedded val doctor: DoctorEntity,
    @Relation(
        parentColumn = Constantes.EMAIL,
        entityColumn = Constantes.NOMBRE,
        associateBy = Junction(HospitalAndDoctorEntity::class)
    )
    val hospitales: List<HospitalEntity>,
)
