package com.example.recyclerview.uicompose.toprated

import com.example.recyclerview.domain.modelo.Pelicula

sealed interface TopRatedEvent {
    sealed class Eventos {
        object LoadPeliculas : Eventos()
    }

    data class TopRatedState(
        val movies: List<Pelicula> = emptyList(),
        val cargando: Boolean = false,
        val error: String? = null,

        )
}
