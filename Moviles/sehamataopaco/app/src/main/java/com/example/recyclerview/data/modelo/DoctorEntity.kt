package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recyclerview.data.common.Constantes

@Entity(tableName = Constantes.DOCTORES)
data class DoctorEntity(
    @ColumnInfo(name = Constantes.NOMBRE)
    val nombre: String,
    @ColumnInfo(name = Constantes.ESPECIALIDAD)
    val especialidad: String,
    @PrimaryKey
    val email: String,
    @ColumnInfo(name = Constantes.FECHA)
    val fecha: String,
)