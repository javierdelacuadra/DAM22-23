package com.example.recyclerview.ui.peliculasactivity.fragments.trending

import com.example.recyclerview.domain.modelo.Pelicula

sealed interface TrendingEvent {
    sealed class Eventos {
        object LoadPeliculas : Eventos()
    }

    data class TrendingState(
        val movies: List<Pelicula>? = emptyList(),
        val cargando: Boolean = false,
        val error: String? = null,
        )
}