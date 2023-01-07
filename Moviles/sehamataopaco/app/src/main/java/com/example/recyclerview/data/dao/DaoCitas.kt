package com.example.recyclerview.data.dao

import androidx.room.*
import com.example.recyclerview.data.modelo.CitaEntity

@Dao
interface DaoCitas {

    @Query("SELECT * FROM citas")
    suspend fun getCitas(): List<CitaEntity>

    @Query("INSERT INTO citas (fecha, hora, emailUsuario, emailDoctor, realizada) VALUES (:fecha, :hora, :emailUsuario, :emailDoctor, :realizada)")
    suspend fun addCita(
        fecha: String,
        hora: String,
        emailUsuario: String,
        emailDoctor: String,
        realizada: Int
    )

    @Update
    suspend fun updateCita(cita: CitaEntity)

    @Delete
    suspend fun deleteCita(cita: CitaEntity)

    @Query("SELECT * FROM citas WHERE id = :id")
    suspend fun getCita(id: Int): CitaEntity

    @Query("SELECT * FROM citas WHERE emailUsuario = :emailUsuario AND realizada = 0")
    suspend fun getCitasByUsuario(emailUsuario: String): List<CitaEntity>

    @Query("UPDATE citas SET realizada = 1 WHERE id = :id")
    suspend fun marcarCita(id: Int): Int

    @Query("SELECT * FROM citas WHERE emailDoctor = :email AND realizada = 0")
    suspend fun getCitasByDoctor(email: String): List<CitaEntity>

    @Query("SELECT * FROM citas WHERE emailDoctor = :email AND realizada = 0")
    suspend fun getCitasByDoctorEmail(email: String): List<CitaEntity>
}