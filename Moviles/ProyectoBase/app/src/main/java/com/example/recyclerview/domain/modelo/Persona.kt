package com.example.recyclerview.domain.modelo

data class Persona(
    val nombre: String,
    val password: String,
    val email: String,
    val tarjetas: List<Tarjeta>? = null
)
