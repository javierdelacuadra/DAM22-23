package com.example.recyclerview.data.modelo

import androidx.room.Embedded
import androidx.room.Relation
import com.example.recyclerview.data.common.Constantes

data class UsuarioWithCitas(
    @Embedded val usuario: UsuarioEntity,
    @Relation(
        parentColumn = Constantes.EMAIL,
        entityColumn = Constantes.EMAIL_USUARIO
    )
    val citas: List<CitaEntity>
)
