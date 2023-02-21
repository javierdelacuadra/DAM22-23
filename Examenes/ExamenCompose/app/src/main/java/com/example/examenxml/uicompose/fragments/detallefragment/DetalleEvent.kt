package com.example.examenxml.ui.fragments.detallefragment

import com.example.examenxml.data.modelo.EnfermedadEntity
import com.example.examenxml.data.modelo.PacienteWithEnfermedades

sealed interface DetalleEvent {
    data class LoadPaciente(val id: String) : DetalleEvent

    data class DetalleState(
        val paciente: PacienteWithEnfermedades? = null,
        val cargando: Boolean = false,
        val mensaje: String? = ""
    )

    data class AddEnfermedad(val enfermedadEntity: EnfermedadEntity) : DetalleEvent
    data class EditarPaciente(val id: String, val nombre: String) : DetalleEvent
}