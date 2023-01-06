package com.example.recyclerview.data.repositories

import com.example.recyclerview.data.dao.DaoCitas
import com.example.recyclerview.data.modelo.toCita
import com.example.recyclerview.data.modelo.toCitaEntity
import com.example.recyclerview.domain.modelo.Cita
import javax.inject.Inject

class CitasRepository @Inject constructor(private val daoCitas: DaoCitas) {

    suspend fun getCitas() = daoCitas.getCitas().map { it.toCita() }

    suspend fun addCita(cita: Cita) = daoCitas.addCita(cita.toCitaEntity())

    suspend fun deleteCita(cita: Cita) = daoCitas.deleteCita(cita.toCitaEntity())

    suspend fun updateCita(cita: Cita) = daoCitas.updateCita(cita.toCitaEntity())

    suspend fun getCita(id: Int) = daoCitas.getCita(id).toCita()

    suspend fun getCitasByUsuario(email: String): List<Cita> =
        daoCitas.getCitasByUsuario(email).map { it.toCita() }

}