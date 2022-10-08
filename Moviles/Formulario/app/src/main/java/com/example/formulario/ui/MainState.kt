package com.example.formulario.ui

import com.example.formulario.domain.modelo.Persona

data class MainState(
    val persona: Persona = Persona(Constantes.CERO, Constantes.NADA, Constantes.NADA, Constantes.NADA, false),
    val mensaje: String? = null
)