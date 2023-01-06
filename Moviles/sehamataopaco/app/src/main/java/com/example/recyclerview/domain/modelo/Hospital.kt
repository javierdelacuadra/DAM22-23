package com.example.recyclerview.domain.modelo

data class Hospital(
    val nombre: String,
    val direccion: String,
    val telefono: Int,
    val doctores: List<Doctor>? = emptyList()
)
