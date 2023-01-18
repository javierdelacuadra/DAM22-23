package com.example.recyclerview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerview.data.dao.DaoCitas
import com.example.recyclerview.data.dao.DaoDoctores
import com.example.recyclerview.data.dao.DaoHospitales
import com.example.recyclerview.data.dao.DaoUsuarios
import com.example.recyclerview.data.modelo.*

@Database(
    entities = [
        UsuarioEntity::class,
        DoctorEntity::class,
        HospitalEntity::class,
        CitaEntity::class,
        LoginEntity::class,
        HospitalAndDoctorEntity::class],
    version = 3, exportSchema = true
)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun daoDoctores(): DaoDoctores
    abstract fun daoHospitales(): DaoHospitales
    abstract fun daoCitas(): DaoCitas
    abstract fun daoUsuarios(): DaoUsuarios

}