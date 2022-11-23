package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tarjetas",
foreignKeys = [
    ForeignKey(entity = PersonaEntity::class,
        parentColumns = ["email"],
        childColumns = ["email"])
        ])
data class TarjetaEntity (
    @PrimaryKey
    val numeroTarjeta: String,

    @ColumnInfo(name = "fechaCaducidad")
    val fechaCaducidad: String,

    @ColumnInfo(name = "cvv")
    val cvv : Int,

    @ColumnInfo(name = "email")
    var email: String,

)