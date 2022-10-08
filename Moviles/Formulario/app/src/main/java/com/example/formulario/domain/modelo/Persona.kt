package com.example.formulario.domain.modelo

data class Persona(
    val id: Int,
    val nombre: String,
    val password: String,
    val email: String,
    val notificaciones: Boolean
)
