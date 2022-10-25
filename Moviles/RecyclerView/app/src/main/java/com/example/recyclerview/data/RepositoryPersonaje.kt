package com.example.recyclerview.data

import com.example.recyclerview.domain.modelo.Personaje
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream
import java.lang.reflect.Type

class RepositoryPersonaje (file: InputStream? = null) {

    private val personajes = mutableListOf<Personaje>()

    init {
        if (personajes.size == 0) {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val listOfCardsType: Type = Types.newParameterizedType(
                MutableList::class.java,
                Personaje::class.java
            )
            val ejemplo = file?.bufferedReader()?.readText()?.let {
                moshi.adapter<List<Personaje>>(listOfCardsType)
                    .fromJson(it)
            }

            ejemplo?.let{ personajes.addAll(it)}
        }
        personajes.add(Personaje("Jorge", 2, 100))
        personajes.add(Personaje("Juan", 3, 200))
        personajes.add(Personaje("Pedro", 4, 300))
    }

    fun addPersonaje(personaje: Personaje): Boolean {
        personajes.stream().anyMatch { p -> p.nombre == personaje.nombre }.let {
            if (it) return false
        }
        personajes.add(personaje)
        return true
    }

    fun getPersonas(): List<Personaje> {
        return personajes
    }

    fun deletePersona(personaje: Personaje): Boolean {
        if (personajes.contains(personaje)) {
            personajes.remove(personaje)
            return true
        }
        return false
    }
}