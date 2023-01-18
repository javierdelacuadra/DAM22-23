package com.example.recyclerview.data.modelo

import androidx.room.Entity

@Entity(tableName = "peliculas")
data class PeliculaEntity(
    val id: Int,
    val title: String,
    val vote_count: Int,
    val vote_average: Double,
    val release_date: String,
    val popularity: Double,
    val overview: String,
    val poster_path: String,
)