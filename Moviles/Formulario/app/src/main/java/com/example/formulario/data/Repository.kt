package com.example.formulario.data

import com.example.formulario.domain.modelo.Persona

object Repository {
    private val personas = mutableListOf<Persona>()

    init {
        personas.add(Persona(1, "Juanito", "1234", "juanito@gmail.com", true))
        personas.add(Persona(2, "Pedrito", "1234", "pedrito@gmail.com", true))
        personas.add(Persona(3, "Pepito", "1234", "pepito@gmail.com", true))
        personas.add(Persona(4, "Pablito", "1234", "pablito@gmail.com", true))
    }

    fun addPersona(persona: Persona): Boolean {
        personas.stream().anyMatch { p -> p.email == persona.email }.let {
            if (it) return false
        }
        personas.add(persona)
        return true
    }

    fun getPersonas(): List<Persona> {
        return personas
    }

    fun deletePersona(persona: Persona): Boolean {
        if (personas.contains(persona)) {
            personas.remove(persona)
            return true
        }
        return false
    }
}