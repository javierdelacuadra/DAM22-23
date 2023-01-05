package com.example.recyclerview.data.modelo

import androidx.room.Embedded
import androidx.room.Relation

data class UsuarioWithCitas(
    @Embedded val usuario: UsuarioEntity,
    @Relation(
        parentColumn = "email",
        entityColumn = "emailUsuario"
    )
    val citas: List<CitaEntity>
)
