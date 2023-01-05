package com.example.recyclerview.data.dao

import androidx.room.*
import com.example.recyclerview.data.modelo.UsuarioEntity
import com.example.recyclerview.data.modelo.UsuarioWithCitas

@Dao
interface DaoUsuarios {

    @Query("SELECT * FROM usuarios")
    suspend fun getUsuarios(): List<UsuarioEntity>

    @Insert
    suspend fun addUsuario(usuario: UsuarioEntity)

    @Update
    suspend fun updateUsuario(usuario: UsuarioEntity)

    @Delete
    suspend fun deleteUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE email = :email")
    suspend fun getUsuario(email: String): UsuarioEntity

    @Transaction
    @Query("SELECT * FROM usuarios")
    suspend fun getUsuariosWithCitas(): List<UsuarioWithCitas>
}