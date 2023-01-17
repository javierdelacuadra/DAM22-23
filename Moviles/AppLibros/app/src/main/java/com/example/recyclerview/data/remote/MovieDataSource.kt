package com.example.recyclerview.data.remote

import com.example.recyclerview.data.modelo.toPelicula
import com.example.recyclerview.domain.modelo.Pelicula
import com.example.recyclerview.network.NetworkResult
import com.example.recyclerview.network.service.MovieService
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val movieService: MovieService) :
    BaseApiResponse() {

    suspend fun fetchTrendingMovies(): NetworkResult<List<Pelicula>> {

        return safeApiCall(apiCall = {movieService.getPopularMovies()},
            transform = { trendingMovieResponse -> trendingMovieResponse
                .results?.map { peliculaEntity ->  peliculaEntity.toPelicula()} ?: emptyList()})
    }
}