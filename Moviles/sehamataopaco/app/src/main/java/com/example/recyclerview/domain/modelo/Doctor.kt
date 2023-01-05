package com.example.recyclerview.domain.modelo

data class Doctor(
    val nombre: String,
    val especialidad: String,
    val email: String,
    val fecha: String,
    val citas: List<Cita>? = emptyList(),
    val hospitales: List<Hospital>? = emptyList()
)
