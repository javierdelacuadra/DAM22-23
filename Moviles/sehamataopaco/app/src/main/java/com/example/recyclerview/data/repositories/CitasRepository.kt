package com.example.recyclerview.data.repositories

import com.example.recyclerview.data.dao.DaoCitas
import com.example.recyclerview.data.modelo.toCita
import com.example.recyclerview.data.modelo.toCitaEntity
import com.example.recyclerview.domain.modelo.Cita
import javax.inject.Inject

class CitasRepository @Inject constructor(private val daoCitas: DaoCitas) {

    suspend fun getCitas() = daoCitas.getCitas().map { it.toCita() }

    suspend fun addCita(cita: Cita) {
        val citaEntity = cita.toCitaEntity()
        return daoCitas.addCita(
            citaEntity.fecha,
            citaEntity.hora,
            citaEntity.emailUsuario,
            citaEntity.emailDoctor,
            citaEntity.realizada
        )
    }

    suspend fun deleteCita(cita: Cita) {
        val citaABorrar = cita.toCitaEntity()
        return daoCitas.deleteCita(
            citaABorrar.fecha,
            citaABorrar.hora,
            citaABorrar.emailUsuario,
            citaABorrar.emailDoctor,
            citaABorrar.realizada
        )
    }

    suspend fun getCitasByUsuario(email: String): List<Cita> =
        daoCitas.getCitasByUsuario(email).map { it.toCita() }

    suspend fun marcarCita(cita: Cita) = daoCitas.marcarCita(cita.toCitaEntity().id)

    suspend fun getCitasByDoctorEmail(email: String): List<Cita> =
        daoCitas.getCitasByDoctorEmail(email).map { it.toCita() }
}