package com.example.recyclerview.ui.compose.trending

import com.example.recyclerview.domain.modelo.Pelicula

data class TrendingState(
    val movies: List<Pelicula> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null,
)