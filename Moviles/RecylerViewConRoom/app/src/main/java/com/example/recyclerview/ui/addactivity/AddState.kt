package com.example.recyclerview.ui.addactivity

import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.ConstantesUI

data class AddState(
    val persona: Persona = Persona(
        ConstantesUI.NADA,
        ConstantesUI.NADA,
        ConstantesUI.NADA,
    ),
    val mensaje: String? = null
)