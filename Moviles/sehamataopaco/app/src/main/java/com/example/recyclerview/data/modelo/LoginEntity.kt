package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login")
data class LoginEntity(
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @PrimaryKey
    val email: String,
    @ColumnInfo(name = "rol")
    val rol: String,
)