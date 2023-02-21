package com.example.examenxml.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examenxml.data.dao.PacientesDao
import com.example.examenxml.data.modelo.EnfermedadEntity
import com.example.examenxml.data.modelo.PacienteEntity

@Database(
    entities = [PacienteEntity::class, EnfermedadEntity::class],
    version = 1, exportSchema = true
)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun daoPacientes(): PacientesDao

}