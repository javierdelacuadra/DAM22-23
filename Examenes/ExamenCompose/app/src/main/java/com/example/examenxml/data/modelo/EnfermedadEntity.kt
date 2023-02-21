package com.example.examenxml.data.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "enfermedades")
data class EnfermedadEntity(
    @PrimaryKey
    val nombre: String,
    val pacienteId: UUID
)