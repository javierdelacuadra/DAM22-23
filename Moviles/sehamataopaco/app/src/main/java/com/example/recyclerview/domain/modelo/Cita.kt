package com.example.recyclerview.domain.modelo

data class Cita(
    val id: Int,
    val fecha: String,
    val hora: String,
    var emailUsuario: String,
    var emailDoctor: String,
    val realizada: Int,
)
