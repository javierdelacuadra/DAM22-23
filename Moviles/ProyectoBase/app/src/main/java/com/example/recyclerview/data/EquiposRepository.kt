package com.example.recyclerview.data

import com.example.recyclerview.data.modelo.toComponente
import com.example.recyclerview.data.modelo.toEquipo
import com.example.recyclerview.data.modelo.toEquipoEntity
import com.example.recyclerview.domain.modelo.Equipo
import javax.inject.Inject

class EquiposRepository @Inject constructor(private val dao: DaoEquipos) {

    suspend fun getEquiposWithComponentes() = dao.getEquiposWithComponentes().map { it.toEquipo() }
    suspend fun getEquipos() = dao.getEquipos().map { it.toEquipo() }
    suspend fun addEquipo(equipo: Equipo) = dao.addEquipo(equipo.toEquipoEntity())
    suspend fun deleteEquipo(nombreEquipo: String) = dao.deleteEquipo(nombreEquipo)

    suspend fun getComponentes(nombreEquipo: String) = dao.getComponentes(nombreEquipo).map { it.toComponente() }
}