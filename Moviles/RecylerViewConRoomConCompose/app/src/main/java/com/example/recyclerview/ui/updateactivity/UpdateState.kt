package com.example.recyclerview.ui.updateactivity

import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.ConstantesUI

class UpdateState(
    val persona: Persona = Persona(
        ConstantesUI.NADA,
        ConstantesUI.NADA,
        ConstantesUI.NADA,
    ),
    val mensaje: String? = null
)