package com.example.recyclerview.data.dao

import androidx.room.*
import com.example.recyclerview.data.modelo.CitaEntity

@Dao
interface DaoCitas {

    @Query("SELECT * FROM citas")
    suspend fun getCitas(): List<CitaEntity>

    @Insert
    suspend fun addCita(cita: CitaEntity)

    @Update
    suspend fun updateCita(cita: CitaEntity)

    @Delete
    suspend fun deleteCita(cita: CitaEntity)

    @Query("SELECT * FROM citas WHERE id = :id")
    suspend fun getCita(id: Int): CitaEntity

    @Query("SELECT * FROM citas WHERE emailUsuario = :emailUsuario AND realizada = 0")
    suspend fun getCitasByUsuario(emailUsuario: String): List<CitaEntity>
}