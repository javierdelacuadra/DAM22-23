package com.example.formulariocompose.ui.pantallas.pantallaadd

import com.example.formulariocompose.domain.modelo.Persona

sealed interface AddEvent {
    data class AddPersona(val persona: Persona) : AddEvent
}