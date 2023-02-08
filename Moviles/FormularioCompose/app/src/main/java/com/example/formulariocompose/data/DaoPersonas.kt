package com.example.formulariocompose.data

import androidx.room.*
import com.example.formulariocompose.data.common.Constantes
import com.example.formulariocompose.data.modelo.PersonaEntity

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