package com.example.recyclerview.data

import androidx.room.*
import com.example.recyclerview.data.modelo.PersonaEntity

@Dao
interface DaoPersonas {

    @Query("SELECT * FROM personas")
    suspend fun getPersonas(): List<PersonaEntity>

    @Insert
    suspend fun addPersona(persona: PersonaEntity)

    @Delete
    suspend fun deletePersona(persona: PersonaEntity)

    @Update
    suspend fun updatePersona(persona: PersonaEntity)

    @Query("SELECT * FROM personas WHERE email = :email")
    suspend fun getPersona(email: String): PersonaEntity
}