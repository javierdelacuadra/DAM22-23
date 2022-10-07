package com.example.formulario.ui

import com.example.formulario.domain.modelo.Persona

data class MainState(
    val persona: Persona = Persona("null", "null", 0, "null"),
    val error: String? = null
)