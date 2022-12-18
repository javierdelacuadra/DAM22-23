package com.example.recyclerview.ui.old.addactivity

import com.example.recyclerview.domain.modelo.Persona

sealed interface AddEvent {
    data class AddPersona(val persona: Persona) : AddEvent
}