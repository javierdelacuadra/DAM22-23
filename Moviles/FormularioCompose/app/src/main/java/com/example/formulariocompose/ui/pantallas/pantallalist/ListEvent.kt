package com.example.formulariocompose.ui.pantallas.pantallalist

import com.example.formulariocompose.domain.modelo.Persona

sealed interface ListEvent {
    object GetPersonas : ListEvent
    object ResetMensaje : ListEvent

    data class DeletePersona(val persona: Persona) : ListEvent
}