package com.example.recyclerview.ui.addactivity

import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.Constantes

data class AddState(
    val persona: Persona = Persona(
        Constantes.NADA,
        Constantes.NADA,
        Constantes.NADA,
    ),
    val mensaje: String? = null
)