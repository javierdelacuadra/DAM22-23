package com.example.recyclerview.ui.listactivity

import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.ConstantesUI

data class ListState(
    val mensaje: String? = ConstantesUI.NADA,
    val lista: List<Persona>?,
)
