package com.example.recyclerview.network.service

import com.example.recyclerview.data.modelo.ListMoviesResponse
import com.example.recyclerview.data.modelo.MovieResponse
import com.example.recyclerview.network.common.ConstantesNetwork
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PeliculasService {

    @GET(ConstantesNetwork.POPULAR_MOVIES)
    suspend fun getPopularMovies(): Response<ListMoviesResponse>

    @GET(ConstantesNetwork.TOP_RATED_MOVIES)
    suspend fun getTopRatedMovies(): Response<ListMoviesResponse>

    @GET(ConstantesNetwork.MOVIE_BY_ID)
    suspend fun getPeliculaByID(@Path(ConstantesNetwork.MOVIE_ID) id: Int): Response<MovieResponse>
}