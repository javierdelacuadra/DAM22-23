package com.example.recyclerview.data.repositories

import com.example.recyclerview.data.remote.MovieDataSource
import com.example.recyclerview.domain.modelo.IndividualMovie
import com.example.recyclerview.domain.modelo.Pelicula
import com.example.recyclerview.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PeliculasRepository @Inject constructor(
    private val movieDataSource: MovieDataSource
) {
    fun fetchTrendingMovies(): Flow<NetworkResult<List<Pelicula>>> {
        return flow {
//            emit(fetchTrendingMoviesCached())
            emit(NetworkResult.Loading())
            val result = movieDataSource.fetchTrendingMovies()
            emit(result)
            if (result is NetworkResult.Success) {
                result.data?.let { it ->
//                    movieDao.deleteAll(it.map { it.toMovieEntity() })
//                    movieDao.insertAll(it.map { it.toMovieEntity() })
                }
            }

        }.flowOn(Dispatchers.IO)
    }

    fun fetchTopRatedMovies(): Flow<NetworkResult<List<Pelicula>>> {
        return flow {
//            emit(fetchTrendingMoviesCached())
            emit(NetworkResult.Loading())
            val result = movieDataSource.fetchTopRatedMovies()
            emit(result)
            if (result is NetworkResult.Success) {
                result.data?.let { it ->
//                    movieDao.deleteAll(it.map { it.toMovieEntity() })
//                    movieDao.insertAll(it.map { it.toMovieEntity() })
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun fetchPeliculaByID(id: Int): Flow<NetworkResult<IndividualMovie>> {
        return flow {
//            emit(fetchTrendingMoviesCached())
            emit(NetworkResult.Loading())
            val result = movieDataSource.fetchPeliculaByID(id)
            emit(result)
            if (result is NetworkResult.Success) {
                result.data?.let { it ->
//                    movieDao.deleteAll(it.map { it.toMovieEntity() })
//                    movieDao.insertAll(it.map { it.toMovieEntity() })
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}