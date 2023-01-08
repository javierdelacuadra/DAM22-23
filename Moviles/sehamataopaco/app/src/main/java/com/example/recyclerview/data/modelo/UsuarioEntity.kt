package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recyclerview.data.common.Constantes

@Entity(tableName = Constantes.USUARIOS)
data class UsuarioEntity(
    @ColumnInfo(name = Constantes.NOMBRE)
    val nombre: String,
    @ColumnInfo(name = Constantes.PASSWORD)
    val password: String,
    @PrimaryKey
    val email: String,
    @ColumnInfo(name = Constantes.TELEFONO)
    val telefono: Int,
    @ColumnInfo(name = Constantes.FECHA)
    val fecha: String,
)