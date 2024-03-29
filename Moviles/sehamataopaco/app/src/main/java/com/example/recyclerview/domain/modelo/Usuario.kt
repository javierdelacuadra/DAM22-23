package com.example.recyclerview.domain.modelo

data class Usuario(
    val nombre: String,
    val password: String,
    val email: String,
    val telefono: Int,
    val fecha: String,
    val citas: List<Cita>? = null
)
