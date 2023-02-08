package com.example.formulariocompose.data

import com.example.formulariocompose.data.modelo.toPersona
import com.example.formulariocompose.data.modelo.toPersonaEntity
import com.example.formulariocompose.domain.modelo.Persona
import javax.inject.Inject

class Repository @Inject constructor(private val dao: DaoPersonas) {

    suspend fun getPersonas() = dao.getPersonas().map { it.toPersona() }

    suspend fun getPersona(email: String) = dao.getPersona(email).toPersona()

    suspend fun addPersona(persona: Persona) = dao.addPersona(persona.toPersonaEntity())

    suspend fun deletePersona(persona: Persona) = dao.deletePersona(persona.toPersonaEntity())

    suspend fun updatePersona(persona: Persona) {
        val personaToUpdate = Persona(
            persona.nombre,
            persona.password.ifEmpty { getPersona(persona.email).password },
            persona.email
        )
        dao.updatePersona(personaToUpdate.toPersonaEntity())
    }
}