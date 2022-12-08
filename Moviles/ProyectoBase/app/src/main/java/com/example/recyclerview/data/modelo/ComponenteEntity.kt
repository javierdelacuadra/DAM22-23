package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "componentes",
    foreignKeys = [
        ForeignKey(
            entity = EquipoEntity::class,
            parentColumns = ["nombre"],
            childColumns = ["nombreEquipo"]
        )
    ]
)
data class ComponenteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "nombreJugador")
    val nombreJugador: String,
    @ColumnInfo(name = "nombreEquipo")
    val nombreEquipo: String,
    @ColumnInfo(name = "tipo")
    val tipo: Int
)