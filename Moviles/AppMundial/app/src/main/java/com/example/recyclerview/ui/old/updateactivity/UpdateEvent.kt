package com.example.recyclerview.ui.old.updateactivity

import com.example.recyclerview.domain.modelo.Persona

sealed interface UpdateEvent {
    data class UpdatePersona(val persona: Persona) : UpdateEvent
}