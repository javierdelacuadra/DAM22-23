package com.example.recyclerview.data.modelo

import androidx.room.Entity

@Entity(
    tableName = "hospitalesDoctores",
    primaryKeys = ["nombre", "email"])
data class HospitalAndDoctorEntity(
    val nombre: String,
    val email: String,
)