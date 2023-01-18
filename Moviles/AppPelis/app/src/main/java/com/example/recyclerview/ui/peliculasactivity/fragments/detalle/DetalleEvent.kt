package com.example.recyclerview.ui.peliculasactivity.fragments.detalle

import com.example.recyclerview.domain.modelo.IndividualMovie

sealed interface DetalleEvent {
    data class LoadPelicula(val id: Int) : DetalleEvent

    data class DetalleState(
        val pelicula: IndividualMovie? = null,
        val cargando: Boolean = false,
        val mensaje: String? = ""
    )
}