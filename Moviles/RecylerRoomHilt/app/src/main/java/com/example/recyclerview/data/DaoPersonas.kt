package com.example.recyclerview.data

import androidx.room.*
import com.example.recyclerview.data.common.Constantes
import com.example.recyclerview.data.modelo.PersonaEntity
import com.example.recyclerview.data.modelo.PersonaWithTarjetas
import com.example.recyclerview.data.modelo.TarjetaEntity

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

    @Update
    suspend fun updatePersona(persona: PersonaWithTarjetas)

    @Query(Constantes.SELECT_PERSONA)
    suspend fun getPersona(email: String): PersonaEntity

    @Transaction
    @Query(Constantes.SELECT_PERSONAS)
    suspend fun getPersonasWithTarjetas(): List<PersonaWithTarjetas>

    @Transaction
    @Query(Constantes.SELECT_PERSONA)
    suspend fun getPersonaWithTarjetas(email: String): PersonaWithTarjetas

    @Transaction
    suspend fun addTarjeta(tarjeta: TarjetaEntity) {
        addTarjeta(tarjeta)
        val persona = getPersonaWithTarjetas(tarjeta.email)
        persona.tarjeta?.toMutableList()?.add(tarjeta)
        updatePersona(persona)
    }

    @Transaction
    @Query(Constantes.SELECT_TARJETAS)
    suspend fun getTarjetas(email: String): List<TarjetaEntity>
}