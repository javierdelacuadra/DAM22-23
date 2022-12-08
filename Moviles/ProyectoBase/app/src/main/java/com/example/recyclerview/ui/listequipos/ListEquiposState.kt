package com.example.recyclerview.ui.listequipos

import com.example.recyclerview.domain.modelo.Equipo

data class ListEquiposState(
    val mensaje: String? = null,
    val lista: List<Equipo>,
)