package com.example.recyclerview.ui.listcomponentes

import com.example.recyclerview.domain.modelo.Componente

data class ListComponentesState(
    val mensaje: String? = null,
    val lista: List<Componente>,
)