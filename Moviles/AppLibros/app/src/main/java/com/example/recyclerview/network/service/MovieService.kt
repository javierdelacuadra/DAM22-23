package com.example.recyclerview.network.service

import com.example.recyclerview.data.modelo.ListMoviesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies() : Response<ListMoviesResponse>
}