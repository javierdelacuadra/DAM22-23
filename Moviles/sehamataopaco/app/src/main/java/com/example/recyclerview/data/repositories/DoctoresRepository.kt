package com.example.recyclerview.data.repositories

import com.example.recyclerview.data.dao.DaoDoctores
import com.example.recyclerview.data.modelo.LoginEntity
import com.example.recyclerview.data.modelo.toDoctor
import com.example.recyclerview.data.modelo.toDoctorEntity
import com.example.recyclerview.domain.modelo.Doctor
import javax.inject.Inject

class DoctoresRepository @Inject constructor(private val dao: DaoDoctores) {

    suspend fun getDoctores() = dao.getDoctores().map { it.toDoctor() }

    private suspend fun getDoctor(email: String) = dao.getDoctor(email).toDoctor()

    suspend fun addDoctor(doctor: Doctor) = dao.addDoctor(doctor.toDoctorEntity())

    suspend fun deleteDoctor(doctor: Doctor) = dao.deleteDoctor(doctor.toDoctorEntity())

    suspend fun updateDoctor(doctor: Doctor) = dao.updateDoctor(doctor.toDoctorEntity())

    suspend fun getDoctorWithHospitales() = dao.getDoctoresWithHospitales().map { it.toDoctor() }

    suspend fun getDoctorWithCitas() = dao.getDoctoresWithCitas().map { it.toDoctor() }

    suspend fun getEspecialidades() = dao.getEspecialidades()

    suspend fun checkLogin(nombre: String, email: String): Boolean {
        val loginCorrecto = dao.checkLogin(nombre, email)
        if (loginCorrecto) {
            val doctor = getDoctor(email)
            val doctorLogin = LoginEntity(doctor.nombre, doctor.email, "doctor")
            addDoctorLogin(doctorLogin)
        }
        return loginCorrecto
    }

    private suspend fun addDoctorLogin(doctor: LoginEntity) = dao.addDoctorLogin(doctor)

    suspend fun getDoctorByName(nombre: String) = dao.getDoctorByName(nombre).toDoctor()
}