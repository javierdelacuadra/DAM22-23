package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "citas",
    foreignKeys = [
        ForeignKey(
            entity = DoctorEntity::class,
            parentColumns = arrayOf("email"),
            childColumns = arrayOf("emailDoctor"),
        ),
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = arrayOf("email"),
            childColumns = arrayOf("emailUsuario"),
        ),
    ]
)
data class CitaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "fecha")
    val fecha: String,
    @ColumnInfo(name = "hora")
    val hora: String,
    @ColumnInfo(name = "emailUsuario")
    val emailUsuario: String,
    @ColumnInfo(name = "emailDoctor")
    val emailDoctor: String,
    @ColumnInfo(name = "realizada")
    val realizada: Int,
) 