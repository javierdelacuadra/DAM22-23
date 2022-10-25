package com.example.recyclerview.ui

import com.example.recyclerview.domain.modelo.Persona

data class MainState(
    val persona: Persona = Persona(Constantes.CERO, Constantes.NADA, Constantes.NADA, Constantes.NADA, false),
    val mensaje: String? = null
)