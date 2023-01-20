package com.example.recyclerview.data.modelo

import com.example.recyclerview.domain.modelo.IndividualMovie
import com.example.recyclerview.domain.modelo.Pelicula

fun PeliculaEntity.toPelicula(): Pelicula {
    return Pelicula(
        this.id,
        this.title,
        this.vote_count,
        this.vote_average,
        this.release_date,
        this.popularity,
        this.overview,
        this.poster_path,
    )
}

fun Pelicula.toPeliculaEntity(): PeliculaEntity {
    return PeliculaEntity(
        this.id,
        this.title,
        this.vote_count,
        this.vote_average,
        this.release_date,
        this.popularity,
        this.overview,
        this.poster_path,
    )
}

fun MovieResponse.toIndividualMovie(): IndividualMovie {
    return IndividualMovie(
        this.poster_path,
        this.id,
        this.original_title,
        this.overview,
        this.release_date,
        this.revenue,
        this.runtime,
        this.vote_average,
        this.vote_count,
    )
}