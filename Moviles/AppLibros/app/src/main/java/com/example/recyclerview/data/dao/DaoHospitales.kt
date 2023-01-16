package com.example.recyclerview.data.dao

import androidx.room.*
import com.example.recyclerview.data.modelo.HospitalEntity
import com.example.recyclerview.data.modelo.HospitalWithDoctores

@Dao
interface DaoHospitales {

    @Query("SELECT * FROM hospitales")
    suspend fun getHospitales(): List<HospitalEntity>

    @Insert
    suspend fun addHospital(hospital: HospitalEntity)

    @Update
    suspend fun updateHospital(hospital: HospitalEntity)

    @Delete
    suspend fun deleteHospital(hospital: HospitalEntity)

    @Query("SELECT * FROM hospitales WHERE nombre = :nombre")
    suspend fun getHospital(nombre: String): HospitalEntity

    @Transaction
    @Query("SELECT * FROM hospitales")
    suspend fun getHospitalesWithDoctores(): List<HospitalWithDoctores>
}