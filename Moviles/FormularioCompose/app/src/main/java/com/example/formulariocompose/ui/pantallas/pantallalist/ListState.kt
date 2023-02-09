package com.example.formulariocompose.ui.pantallas.pantallalist

import com.example.formulariocompose.domain.modelo.Persona
import com.example.formulariocompose.ui.common.ConstantesUI

data class ListState(
    val mensaje: String? = ConstantesUI.NADA,
    val lista: List<Persona> = emptyList(),
)
