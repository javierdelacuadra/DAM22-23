package com.example.formulario.data

import com.example.formulario.domain.modelo.Persona

object Repository {
    private val personas = mutableMapOf<String, Persona>()

    fun addPersona(persona: Persona) =
        personas.put(persona.nombre, persona)


    fun getPersonas(): List<Persona> {
        return personas.values.toList()
    }

    fun deletePersona(persona: Persona) =
        personas.remove(persona.nombre)

//    fun updatePersona(persona: Persona) {
//        personas.remove(persona)
//        personas.add(persona)
//    }
}