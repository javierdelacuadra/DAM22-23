package com.example.recyclerview.data.remote

import com.example.recyclerview.data.modelo.toIndividualMovie
import com.example.recyclerview.data.modelo.toPelicula
import com.example.recyclerview.domain.modelo.IndividualMovie
import com.example.recyclerview.domain.modelo.Pelicula
import com.example.recyclerview.network.NetworkResult
import com.example.recyclerview.network.service.MovieService
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val movieService: MovieService) :
    BaseApiResponse() {

    suspend fun fetchTrendingMovies(): NetworkResult<List<Pelicula>> {
        return safeApiCall(apiCall = { movieService.getPopularMovies() },
            transform = { trendingMovieResponse ->
                trendingMovieResponse
                    .results?.map { peliculaEntity -> peliculaEntity.toPelicula() } ?: emptyList()
            })
    }

    suspend fun fetchTopRatedMovies(): NetworkResult<List<Pelicula>> {
        return safeApiCall(apiCall = { movieService.getTopRatedMovies() },
            transform = { trendingMovieResponse ->
                trendingMovieResponse
                    .results?.map { peliculaEntity -> peliculaEntity.toPelicula() } ?: emptyList()
            })
    }

    suspend fun fetchPeliculaByID(id: Int): NetworkResult<IndividualMovie> {
        return safeApiCall(apiCall = { movieService.getPeliculaByID(id) },
            transform = { trendingMovieResponse ->
                trendingMovieResponse.toIndividualMovie()
            })
    }
}