package com.example.recyclerview.ui.listactivity

import com.example.recyclerview.domain.modelo.Persona

sealed interface ListEvent {
    object GetPersonas : ListEvent
    data class DeletePersona(val persona: Persona) : ListEvent
}