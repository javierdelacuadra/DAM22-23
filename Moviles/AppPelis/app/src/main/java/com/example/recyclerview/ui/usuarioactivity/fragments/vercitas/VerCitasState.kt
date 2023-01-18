package com.example.recyclerview.ui.usuarioactivity.fragments.vercitas

import com.example.recyclerview.domain.modelo.Cita

data class VerCitasState(
    val mensaje: String?,
    val citas: List<Cita>
)