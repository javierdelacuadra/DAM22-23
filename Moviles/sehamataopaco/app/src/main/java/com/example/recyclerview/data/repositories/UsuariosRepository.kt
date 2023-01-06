package com.example.recyclerview.data.repositories

import com.example.recyclerview.data.dao.DaoUsuarios
import com.example.recyclerview.data.modelo.LoginEntity
import com.example.recyclerview.data.modelo.toUsuario
import com.example.recyclerview.data.modelo.toUsuarioEntity
import com.example.recyclerview.domain.modelo.Usuario
import javax.inject.Inject

class UsuariosRepository @Inject constructor(private val dao: DaoUsuarios) {

    suspend fun getUsuarios() = dao.getUsuarios().map { it.toUsuario() }

    private suspend fun getUsuario(email: String) = dao.getUsuario(email).toUsuario()

    private suspend fun getUsuarioByNombre(nombre: String) =
        dao.getUsuarioByNombre(nombre).toUsuario()

    suspend fun addUsuario(usuario: Usuario) = dao.addUsuario(usuario.toUsuarioEntity())

    suspend fun deleteUsuario(usuario: Usuario) = dao.deleteUsuario(usuario.toUsuarioEntity())

    suspend fun updateUsuario(usuario: Usuario) = dao.updateUsuario(usuario.toUsuarioEntity())

    suspend fun getUsuariosWithCitas() = dao.getUsuariosWithCitas().map { it.toUsuario() }

    suspend fun checkLogin(nombre: String, password: String): Boolean {
        val loginCorrecto = dao.checkLogin(nombre, password)
        if (loginCorrecto) {
            val usuario = getUsuarioByNombre(nombre)
            val usuarioLogin = LoginEntity(usuario.nombre, usuario.email, "usuario")
            addUsuarioLogin(usuarioLogin)
        }
        return loginCorrecto
    }

    suspend fun addUsuarioLogin(usuario: LoginEntity) = dao.addUsuarioLogin(usuario)

    suspend fun getUsuarioActual() = dao.getUsuarioActual()
}