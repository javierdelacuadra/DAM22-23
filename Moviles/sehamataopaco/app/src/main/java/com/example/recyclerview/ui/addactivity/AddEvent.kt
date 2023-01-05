package com.example.recyclerview.ui.addactivity

import com.example.recyclerview.domain.modelo.Persona

sealed interface AddEvent {
    data class AddPersona(val persona: Persona) : AddEvent
}