package com.example.recyclerview.domain.modelo

data class Hospital(
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val doctores: List<Doctor>? = emptyList()
)
