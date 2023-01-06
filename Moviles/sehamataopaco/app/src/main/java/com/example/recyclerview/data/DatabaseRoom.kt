package com.example.recyclerview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerview.data.dao.*
import com.example.recyclerview.data.modelo.*

@Database(
    entities = [
        PersonaEntity::class,
        TarjetaEntity::class,
        UsuarioEntity::class,
        DoctorEntity::class,
        HospitalEntity::class,
        CitaEntity::class,
        LoginEntity::class,
        HospitalAndDoctorEntity::class],
    version = 1, exportSchema = true
)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun daoDoctores(): DaoDoctores
    abstract fun daoHospitales(): DaoHospitales
    abstract fun daoCitas(): DaoCitas
    abstract fun daoUsuarios(): DaoUsuarios
    abstract fun daoPersonas(): DaoPersonas

}