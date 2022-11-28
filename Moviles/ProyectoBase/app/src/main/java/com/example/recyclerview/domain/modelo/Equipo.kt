package com.example.recyclerview.domain.modelo

data class Equipo(
    val nombre: String,
    val nacionalidad: String,
    val puesto: Int,
    val componentes: List<Componente>? = null
)
