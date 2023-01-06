package com.example.recyclerview.ui.usuarioactivity.fragments.vercitas

import com.example.recyclerview.domain.modelo.Cita

sealed interface VerCitasEvent {
    data class CancelarCita(val cita: Cita) : VerCitasEvent
    object GetCitas : VerCitasEvent
}