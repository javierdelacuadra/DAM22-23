package com.example.recyclerview.data

import com.example.recyclerview.data.modelo.toPersona
import com.example.recyclerview.data.modelo.toPersonaEntity
import com.example.recyclerview.domain.modelo.Persona

class Repository (private val dao: DaoPersonas) {

    suspend fun getPersonas() = dao.getPersonas().map { it.toPersona() }

    suspend fun addPersona(persona: Persona) = dao.addPersona(persona.toPersonaEntity())

    suspend fun deletePersona(persona: Persona) = dao.deletePersona(persona.toPersonaEntity())

    suspend fun updatePersona(persona: Persona) = dao.updatePersona(persona.toPersonaEntity())

}