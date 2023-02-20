package com.example.recyclerview.uicompose.trending

sealed interface TrendingEvent {
    sealed class Eventos {
        object LoadPeliculas : Eventos()
    }
}