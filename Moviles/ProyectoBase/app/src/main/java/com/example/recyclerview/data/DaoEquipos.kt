package com.example.recyclerview.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.recyclerview.data.modelo.ComponenteEntity
import com.example.recyclerview.data.modelo.EquipoEntity
import com.example.recyclerview.data.modelo.EquipoWithComponentes

@Dao
interface DaoEquipos {
    @Transaction
    @Query("SELECT * FROM equipos")
    suspend fun getEquiposWithComponentes(): List<EquipoWithComponentes>

    @Query("SELECT * FROM equipos")
    suspend fun getEquipos(): List<EquipoEntity>

    @Insert
    suspend fun addEquipo(equipo: EquipoEntity)

    @Query("DELETE FROM equipos WHERE nombre = :nombreEquipo")
    suspend fun deleteEquipo(nombreEquipo: String)

    @Query("SELECT * FROM componentes WHERE nombreEquipo = :nombreEquipo")
    suspend fun getComponentes(nombreEquipo: String): List<ComponenteEntity>
}