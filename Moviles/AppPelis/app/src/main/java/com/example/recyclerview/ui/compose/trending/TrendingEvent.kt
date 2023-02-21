package com.example.recyclerview.ui.compose.trending

sealed interface TrendingEvent {
    sealed class Eventos {
        object LoadPeliculas : Eventos()
    }
}