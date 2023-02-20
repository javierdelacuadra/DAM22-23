package com.example.recyclerview.data.modelo

data class MovieResponse(
    val poster_path: String?,
    val id: Int?,
    val original_title: String?,
    val overview: String?,
    val release_date: String?,
    val revenue: Long?,
    val runtime: Int?,
    val vote_average: Double?,
    val vote_count: Int?
)