package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recyclerview.data.common.Constantes

@Entity(tableName = Constantes.LOGIN)
data class LoginEntity(
    @ColumnInfo(name = Constantes.NOMBRE)
    val nombre: String,
    @PrimaryKey
    val email: String,
    @ColumnInfo(name = Constantes.ROL)
    val rol: String,
)