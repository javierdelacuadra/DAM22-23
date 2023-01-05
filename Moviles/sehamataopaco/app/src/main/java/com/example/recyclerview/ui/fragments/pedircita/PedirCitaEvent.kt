package com.example.recyclerview.ui.fragments.pedircita

sealed interface PedirCitaEvent {
    data class GetDoctores(val selectedItem: String) : PedirCitaEvent
    data class GetHours(val nombreDoctor: String) : PedirCitaEvent
    object GetEspecialidades : PedirCitaEvent
}