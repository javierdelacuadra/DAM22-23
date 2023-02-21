package com.example.examenxml.data.dao

import androidx.room.*
import com.example.examenxml.data.modelo.PacienteEntity

@Dao
interface PacientesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<PacienteEntity>)

    @Delete
    fun deleteAll(movie: List<PacienteEntity>)

    @Query("SELECT * FROM pacientes")
    fun getAll(): List<PacienteEntity>

    @Query("SELECT * FROM pacientes WHERE id = :id")
    fun getPacienteById(id: String): PacienteEntity

}