package com.example.examenxml.uicompose.fragments.pacientesfragment

import com.example.examenxml.domain.modelo.Paciente

sealed interface PacienteEvent {
    data class PacienteState(
        val cargando: Boolean = false,
        val error: String? = "",
        val pacientes: List<Paciente>? = emptyList()
    )

    sealed class Eventos {
        object LoadPacientes : Eventos()
    }
}