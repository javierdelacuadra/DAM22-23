package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class EquipoEntity(
    @PrimaryKey
    val nombre: String,
    @ColumnInfo(name = "nacionalidad")
    val nacionalidad: String,
    @ColumnInfo(name = "puesto")
    val puesto: Int
)