package com.example.recyclerview.domain.modelo

data class Tarjeta(
    val numeroTarjeta: String,
    val fechaCaducidad: String,
    val cvv: Int,
    val email: String,
)