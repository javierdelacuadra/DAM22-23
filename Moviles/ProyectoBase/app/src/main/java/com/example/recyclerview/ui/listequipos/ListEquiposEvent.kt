package com.example.recyclerview.ui.listequipos

sealed interface ListEquiposEvent {
    object GetEquipos : ListEquiposEvent
    data class DeleteEquipos(val nombreEquipo: String) : ListEquiposEvent
}