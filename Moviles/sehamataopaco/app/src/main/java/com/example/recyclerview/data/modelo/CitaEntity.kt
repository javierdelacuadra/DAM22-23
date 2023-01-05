package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class CitaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "fecha")
    val fecha: String,
    @ColumnInfo(name = "hora")
    val hora: String,
    @ColumnInfo(name = "emailUsuario")
    val emailUsuario: String,
    @ColumnInfo(name = "emailDoctor")
    val emailDoctor: String,
)