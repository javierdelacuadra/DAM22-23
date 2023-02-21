package com.example.examenxml.data.dao

import androidx.room.*
import com.example.examenxml.data.modelo.EnfermedadEntity
import com.example.examenxml.data.modelo.PacienteEntity
import com.example.examenxml.data.modelo.PacienteWithEnfermedades
import java.util.UUID

@Dao
interface PacientesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<PacienteEntity>)

    @Delete
    fun deleteAll(movie: List<PacienteEntity>)

    @Query("SELECT * FROM pacientes")
    fun getAll(): List<PacienteEntity>

    @Query("SELECT * FROM pacientes WHERE id = :id")
    fun getPacienteById(id: UUID): PacienteEntity

    @Transaction
    @Query("SELECT * FROM pacientes WHERE id = :id")
    fun getPacienteByIdWithEnfermedades(id: UUID): PacienteWithEnfermedades

    fun insertAllEnfermedades(enfermedades: List<EnfermedadEntity>) {
        enfermedades.forEach { insertEnfermedad(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEnfermedad(enfermedad: EnfermedadEntity)

    //update paciente only name
    @Query("UPDATE pacientes SET nombre = :nombre WHERE id = :id")
    fun updatePaciente(id: UUID, nombre: String)

}