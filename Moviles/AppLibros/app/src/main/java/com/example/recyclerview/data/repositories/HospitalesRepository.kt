package com.example.recyclerview.data.repositories

import com.example.recyclerview.data.dao.DaoHospitales
import com.example.recyclerview.data.modelo.toHospital
import com.example.recyclerview.data.modelo.toHospitalEntity
import com.example.recyclerview.domain.modelo.Hospital
import javax.inject.Inject

class HospitalesRepository @Inject constructor(private val dao: DaoHospitales) {

    suspend fun getHospitales() = dao.getHospitales().map { it.toHospital() }

    private suspend fun getHospital(nombre: String) = dao.getHospital(nombre).toHospital()

    suspend fun addHospital(hospital: Hospital) = dao.addHospital(hospital.toHospitalEntity())

    suspend fun deleteHospital(hospital: Hospital) = dao.deleteHospital(hospital.toHospitalEntity())

    suspend fun updateHospital(hospital: Hospital) = dao.updateHospital(hospital.toHospitalEntity())

    suspend fun getHospitalesWithDoctores() =
        dao.getHospitalesWithDoctores().map { it.toHospital() }
}