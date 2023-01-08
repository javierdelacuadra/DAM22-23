package com.example.recyclerview.data.modelo

import androidx.room.Entity
import com.example.recyclerview.data.common.Constantes

@Entity(
    tableName = Constantes.HOSPITALES_DOCTORES,
    primaryKeys = [Constantes.NOMBRE, Constantes.EMAIL],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = HospitalEntity::class,
            parentColumns = [Constantes.NOMBRE],
            childColumns = [Constantes.NOMBRE],
        ),
        androidx.room.ForeignKey(
            entity = DoctorEntity::class,
            parentColumns = [Constantes.EMAIL],
            childColumns = [Constantes.EMAIL],
        ),
    ]
)
data class HospitalAndDoctorEntity(
    val nombre: String,
    val email: String,
)