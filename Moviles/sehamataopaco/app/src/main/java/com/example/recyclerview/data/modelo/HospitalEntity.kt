package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hospitales")
data class HospitalEntity(
    @PrimaryKey
    val nombre: String,
    @ColumnInfo(name = "direccion")
    val direccion: String,
    @ColumnInfo(name = "telefono")
    val telefono: Int,
)