package com.example.examenxml.ui.fragments.detallefragment

import com.example.examenxml.domain.modelo.Paciente

sealed interface DetalleEvent {
    data class LoadPaciente(val id: String) : DetalleEvent

    data class DetalleState(
        val paciente: Paciente? = null,
        val cargando: Boolean = false,
        val mensaje: String? = ""
    )
}