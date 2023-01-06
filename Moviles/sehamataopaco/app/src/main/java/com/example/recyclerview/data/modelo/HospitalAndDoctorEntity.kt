package com.example.recyclerview.data.modelo

import androidx.room.Entity

@Entity(
    tableName = "hospitalesDoctores",
    primaryKeys = ["nombre", "email"],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = HospitalEntity::class,
            parentColumns = ["nombre"],
            childColumns = ["nombre"],
        ),
        androidx.room.ForeignKey(
            entity = DoctorEntity::class,
            parentColumns = ["email"],
            childColumns = ["email"],
        ),
    ]
)
data class HospitalAndDoctorEntity(
    val nombre: String,
    val email: String,
)