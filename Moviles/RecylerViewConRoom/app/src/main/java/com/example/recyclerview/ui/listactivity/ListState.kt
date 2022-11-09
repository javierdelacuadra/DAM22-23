package com.example.recyclerview.ui.listactivity

import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.Constantes

data class ListState(
    val mensaje: String? = Constantes.NADA,
    val lista: List<Persona>?,
)
