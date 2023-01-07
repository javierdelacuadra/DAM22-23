package com.example.recyclerview.ui.usuarioactivity.fragments.pedircita

import com.example.recyclerview.domain.modelo.Cita

sealed interface PedirCitaEvent {
    object GetEspecialidades : PedirCitaEvent
    data class GetDoctores(val especialidad: String) : PedirCitaEvent
    data class GetFechas(val nombreDoctor: String) : PedirCitaEvent
    data class GetHours(val fecha: String, val nombreDoctor: String) : PedirCitaEvent

    data class PedirCita(val cita: Cita) : PedirCitaEvent

    object ClearStateButEspecialidad : PedirCitaEvent
    object ClearStateButEspecialidadAndDoctor : PedirCitaEvent
    object ClearStateButEspecialidadAndDoctorAndFecha : PedirCitaEvent
}