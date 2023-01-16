package com.example.recyclerview.ui.doctoractivity.fragments.revisarcitas

import com.example.recyclerview.domain.modelo.Cita

data class RevisarCitasState(
    val mensaje: String?,
    val citas: List<Cita>
)
