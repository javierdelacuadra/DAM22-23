package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctores")
data class DoctorEntity(
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "especialidad")
    val especialidad: String,
    @PrimaryKey
    val email: String,
    @ColumnInfo(name = "fecha")
    val fecha: String,
)