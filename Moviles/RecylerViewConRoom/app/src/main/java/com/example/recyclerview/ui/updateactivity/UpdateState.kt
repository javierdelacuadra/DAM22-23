package com.example.recyclerview.ui.updateactivity

import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.Constantes

class UpdateState (
    val persona: Persona = Persona(
        Constantes.NADA,
        Constantes.NADA,
        Constantes.NADA,),
    val mensaje: String? = null
)