package com.example.recyclerview.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.recyclerview.data.common.Constantes

@Entity(
    tableName = Constantes.CITAS,
    foreignKeys = [
        ForeignKey(
            entity = DoctorEntity::class,
            parentColumns = arrayOf(Constantes.EMAIL),
            childColumns = arrayOf(Constantes.EMAIL_DOCTOR),
        ),
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = arrayOf(Constantes.EMAIL),
            childColumns = arrayOf(Constantes.EMAIL_USUARIO),
        ),
    ]
)
data class CitaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constantes.ID)
    val id: Int,
    @ColumnInfo(name = Constantes.FECHA)
    val fecha: String,
    @ColumnInfo(name = Constantes.HORA)
    val hora: String,
    @ColumnInfo(name = Constantes.EMAIL_USUARIO)
    val emailUsuario: String,
    @ColumnInfo(name = Constantes.EMAIL_DOCTOR)
    val emailDoctor: String,
    @ColumnInfo(name = Constantes.REALIZADA)
    val realizada: Int,
) 