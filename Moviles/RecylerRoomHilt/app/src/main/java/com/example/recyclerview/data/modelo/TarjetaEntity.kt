package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.recyclerview.data.common.Constantes

@Entity(
    tableName = Constantes.TABLA_TARJETAS,
    foreignKeys = [
        ForeignKey(
            entity = PersonaEntity::class,
            parentColumns = [Constantes.EMAIL],
            childColumns = [Constantes.EMAIL],
        )
    ]
)
data class TarjetaEntity(
    @PrimaryKey
    val numeroTarjeta: String,

    @ColumnInfo(name = Constantes.FECHA_CADUCIDAD)
    val fechaCaducidad: String,

    @ColumnInfo(name = Constantes.CVV)
    val cvv: Int,

    @ColumnInfo(name = Constantes.EMAIL)
    var email: String,
)