package com.example.recyclerview.data

import com.example.recyclerview.domain.modelo.Persona

object Repository {
    private val personas = mutableListOf<Persona>()

    init {
        personas.add(Persona("Juanito", "1234", "juanito@gmail.com"))
        personas.add(Persona("Pedrito", "1234", "pedrito@gmail.com"))
        personas.add(Persona("Pepito", "1234", "pepito@gmail.com"))
        personas.add(Persona("Pablito", "1234", "pablito@gmail.com"))
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

    fun getPersona(email: String): Persona? {
        return personas.find { it.email == email }
    }

    fun deletePersona(email: String): Boolean {
        return personas.removeIf { it.email == email }
    }

    fun updatePersona(persona: Persona): Boolean {
        personas.stream().anyMatch { p -> p.email == persona.email }.let {
            if (!it) return false
        }
        personas.removeIf { it.email == persona.email }
        personas.add(persona)
        return true
    }
}