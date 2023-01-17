package com.example.recyclerview.domain.modelo

data class Pelicula(
    val id: Int,
    val title: String,
    val vote_count: Int,
    val vote_average: Double,
    val release_date: String,
    val popularity: Double,
    val overview: String,
)
