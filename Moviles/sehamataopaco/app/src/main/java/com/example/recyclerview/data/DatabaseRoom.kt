package com.example.recyclerview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerview.data.dao.DaoCitas
import com.example.recyclerview.data.dao.DaoDoctores
import com.example.recyclerview.data.dao.DaoHospitales
import com.example.recyclerview.data.dao.DaoUsuarios
import com.example.recyclerview.data.modelo.CitaEntity
import com.example.recyclerview.data.modelo.DoctorEntity
import com.example.recyclerview.data.modelo.HospitalEntity
import com.example.recyclerview.data.modelo.UsuarioEntity

@Database(
    entities = [
        UsuarioEntity::class,
        DoctorEntity::class,
        HospitalEntity::class,
        CitaEntity::class,
    ], version = 1, exportSchema = true
)
abstract class DatabaseRoom : RoomDatabase() {

    abstract fun daoDoctores(): DaoDoctores
    abstract fun daoHospitales(): DaoHospitales
    abstract fun daoCitas(): DaoCitas
    abstract fun daoUsuarios(): DaoUsuarios

}