package com.example.recyclerview.ui.listactivity

import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.common.Constantes

class ListState(
    val error: String? = Constantes.NADA,
    val lista: List<Persona>?,
)
