package com.example.recyclerview.data.dao

import androidx.room.*
import com.example.recyclerview.data.modelo.LoginEntity
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

    @Query("SELECT EXISTS(SELECT * FROM usuarios WHERE nombre = :nombre AND password = :password)")
    suspend fun checkLogin(nombre: String, password: String): Boolean

    @Insert
    suspend fun addUsuarioLogin(usuario: LoginEntity)

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre")
    suspend fun getUsuarioByNombre(nombre: String): UsuarioEntity

    @Query("SELECT * FROM login LIMIT 1")
    suspend fun getUsuarioActual(): LoginEntity

    @Query("DELETE FROM login")
    suspend fun cerrarSesion()
}