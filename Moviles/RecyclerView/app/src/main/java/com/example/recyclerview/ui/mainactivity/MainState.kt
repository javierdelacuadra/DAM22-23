package com.example.recyclerview.ui.mainactivity

import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.Constantes

data class MainState(
    val persona: Persona = Persona(
        Constantes.NADA,
        Constantes.NADA,
        Constantes.NADA,),
    val mensaje: String? = null
)