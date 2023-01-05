package com.example.recyclerview.data.repositories

import com.example.recyclerview.data.dao.DaoDoctores
import com.example.recyclerview.data.modelo.toDoctor
import com.example.recyclerview.data.modelo.toDoctorEntity
import com.example.recyclerview.domain.modelo.Doctor
import javax.inject.Inject

class DoctoresRepository @Inject constructor(private val dao: DaoDoctores) {

    suspend fun getDoctores() = dao.getDoctores().map { it.toDoctor() }

    suspend fun getDoctor(email: String) = dao.getDoctor(email).toDoctor()

    suspend fun addDoctor(doctor: Doctor) = dao.addDoctor(doctor.toDoctorEntity())

    suspend fun deleteDoctor(doctor: Doctor) = dao.deleteDoctor(doctor.toDoctorEntity())

    suspend fun updateDoctor(doctor: Doctor) = dao.updateDoctor(doctor.toDoctorEntity())

    suspend fun getDoctorWithHospitales() = dao.getDoctoresWithHospitales().map { it.toDoctor() }

    suspend fun getDoctorWithCitas() = dao.getDoctoresWithCitas().map { it.toDoctor() }

    suspend fun getEspecialidades() = dao.getEspecialidades()

    suspend fun getHoras(nombreDoctor: String): MutableList<String> {
        val horasDisponibles = mutableListOf<String>()
        for (hora in 9..19) {
            horasDisponibles.add(hora.toString())
        }

        val horasNoDisponibles = dao.getHoras(nombreDoctor)
        horasDisponibles.removeAll(horasNoDisponibles)
        horasDisponibles.forEachIndexed { index, s ->
            horasDisponibles[index] = "$s:00"
        }

        return horasDisponibles
    }
}