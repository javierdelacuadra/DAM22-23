package com.example.recyclerview.data.dao

import androidx.room.*
import com.example.recyclerview.data.modelo.PeliculaEntity

@Dao
interface DaoPeliculas {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<PeliculaEntity>)

    @Delete
    fun deleteAll(movie: List<PeliculaEntity>)

    @Query("SELECT * FROM peliculas order by popularity DESC")
    fun getAll(): List<PeliculaEntity>
}