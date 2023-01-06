package com.example.recyclerview.ui.usuarioactivity

import com.example.recyclerview.domain.modelo.Doctor

data class LaunchState(
    val mensaje: String? = null,
    val lista: List<Doctor>,
)
