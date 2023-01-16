package com.example.recyclerview.ui.doctoractivity.fragments.revisarcitas

import com.example.recyclerview.domain.modelo.Cita

sealed interface RevisarCitasEvent {
    object GetCitas : RevisarCitasEvent
    data class MarcarComoRealizada(val cita: Cita) : RevisarCitasEvent
}