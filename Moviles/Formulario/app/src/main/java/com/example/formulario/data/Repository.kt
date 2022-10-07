package com.example.formulario.data

import com.example.formulario.domain.modelo.Persona

object Repository {
    private val personas = mutableMapOf<String, Persona>()

    init {
        personas["1"] = Persona("Juan", "Perez", "12345678", true)
        personas["2"] = Persona("Maria", "Gomez", "87654321", false)
    }

    fun addPersona(persona: Persona): Boolean {
        if (personas.containsKey(persona.email)) {
            return false
        }
        personas[persona.email] = persona
        return true
    }


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