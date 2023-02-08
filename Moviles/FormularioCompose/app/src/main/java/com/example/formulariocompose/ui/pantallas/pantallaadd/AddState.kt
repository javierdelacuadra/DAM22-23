package com.example.formulariocompose.ui.pantallas.pantallaadd

import com.example.formulariocompose.domain.modelo.Persona
import com.example.formulariocompose.ui.common.ConstantesUI

data class AddState(
    val persona: Persona = Persona(
        ConstantesUI.NADA,
        ConstantesUI.NADA,
        ConstantesUI.NADA,
    ),
    val mensaje: String? = null
)