package com.example.examenxml.data.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pacientes")
data class PacienteEntity (
    @PrimaryKey
    val id: UUID,
    var nombre: String,
    val dni: String,
)