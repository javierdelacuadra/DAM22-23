package com.example.recyclerview.ui.launch

import com.example.recyclerview.domain.modelo.Doctor

data class LaunchState(
    val mensaje: String? = null,
    val lista: List<Doctor>,
)
