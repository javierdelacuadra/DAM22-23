package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "password")
    val password: String,
    @PrimaryKey
    val email: String,
    @ColumnInfo(name = "telefono")
    val telefono: String,
    @ColumnInfo(name = "fecha")
    val fecha: String,
)