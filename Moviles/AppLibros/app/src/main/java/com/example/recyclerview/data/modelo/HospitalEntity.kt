package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recyclerview.data.common.Constantes

@Entity(tableName = Constantes.HOSPITALES)
data class HospitalEntity(
    @PrimaryKey
    val nombre: String,
    @ColumnInfo(name = Constantes.DIRECCION)
    val direccion: String,
    @ColumnInfo(name = Constantes.TELEFONO)
    val telefono: Int,
)