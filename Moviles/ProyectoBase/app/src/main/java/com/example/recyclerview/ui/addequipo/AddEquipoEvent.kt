package com.example.recyclerview.ui.addequipo

import com.example.recyclerview.domain.modelo.Equipo

sealed interface AddEquipoEvent {
    data class AddEquipo(val equipo: Equipo) : AddEquipoEvent
}