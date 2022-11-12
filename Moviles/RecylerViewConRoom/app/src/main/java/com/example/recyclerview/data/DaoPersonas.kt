package com.example.recyclerview.data

import androidx.room.*
import com.example.recyclerview.data.common.Constantes
import com.example.recyclerview.data.modelo.PersonaEntity

@Dao
interface DaoPersonas {

    @Query(Constantes.SELECT_PERSONAS)
    suspend fun getPersonas(): List<PersonaEntity>

    @Insert
    suspend fun addPersona(persona: PersonaEntity)

    @Delete
    suspend fun deletePersona(persona: PersonaEntity)

    @Update
    suspend fun updatePersona(persona: PersonaEntity)

    @Query(Constantes.SELECT_PERSONA)
    suspend fun getPersona(email: String): PersonaEntity
}