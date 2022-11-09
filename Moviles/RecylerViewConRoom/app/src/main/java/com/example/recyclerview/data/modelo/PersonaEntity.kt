package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personas")
data class PersonaEntity (
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "password")
    val password: String,
    @PrimaryKey
    val email: String,
)