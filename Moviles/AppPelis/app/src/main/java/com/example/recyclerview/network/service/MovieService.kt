package com.example.recyclerview.network.service

import com.example.recyclerview.data.modelo.ListMoviesResponse
import com.example.recyclerview.data.modelo.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<ListMoviesResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(): Response<ListMoviesResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getPeliculaByID(@Path("movie_id") id: Int): Response<MovieResponse>
}