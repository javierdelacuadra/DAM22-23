package com.example.recyclerview.ui.old.listactivity

import com.example.recyclerview.domain.modelo.Persona

data class ListState(
    val mensaje: String? = null,
    val lista: List<Persona>,
)
