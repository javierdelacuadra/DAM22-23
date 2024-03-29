package com.example.formulariocompose.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.formulariocompose.data.common.Constantes

@Entity(tableName = Constantes.TABLE_NAME)
data class PersonaEntity(
    @ColumnInfo(name = Constantes.NOMBRE)
    val nombre: String,
    @ColumnInfo(name = Constantes.PASSWORD)
    val password: String,
    @PrimaryKey
    val email: String,
)