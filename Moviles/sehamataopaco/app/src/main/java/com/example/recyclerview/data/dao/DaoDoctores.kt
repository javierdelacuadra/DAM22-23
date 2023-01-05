package com.example.recyclerview.data.dao

import androidx.room.*
import com.example.recyclerview.data.modelo.DoctorEntity
import com.example.recyclerview.data.modelo.DoctorWithCitas
import com.example.recyclerview.data.modelo.DoctorWithHospitales

@Dao
interface DaoDoctores {

    @Query("SELECT * FROM doctores")
    suspend fun getDoctores(): List<DoctorEntity>

    @Insert
    suspend fun addDoctor(doctor: DoctorEntity)

    @Update
    suspend fun updateDoctor(doctor: DoctorEntity)

    @Delete
    suspend fun deleteDoctor(doctor: DoctorEntity)

    @Query("SELECT * FROM doctores WHERE email = :email")
    suspend fun getDoctor(email: String): DoctorEntity

    @Transaction
    @Query("SELECT * FROM doctores")
    suspend fun getDoctoresWithCitas(): List<DoctorWithCitas>

    @Transaction
    @Query("SELECT * FROM doctores")
    suspend fun getDoctoresWithHospitales(): List<DoctorWithHospitales>
}