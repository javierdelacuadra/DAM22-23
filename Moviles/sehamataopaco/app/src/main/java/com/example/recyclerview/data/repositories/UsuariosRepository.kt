package com.example.recyclerview.data.repositories

import com.example.recyclerview.data.common.Constantes
import com.example.recyclerview.data.dao.DaoUsuarios
import com.example.recyclerview.data.modelo.LoginEntity
import com.example.recyclerview.data.modelo.toUsuario
import javax.inject.Inject

class UsuariosRepository @Inject constructor(private val dao: DaoUsuarios) {


    private suspend fun getUsuarioByNombre(nombre: String) =
        dao.getUsuarioByNombre(nombre).toUsuario()

    suspend fun checkLogin(nombre: String, password: String): Boolean {
        val loginCorrecto = dao.checkLogin(nombre, password)
        if (loginCorrecto) {
            val usuario = getUsuarioByNombre(nombre)
            val usuarioLogin = LoginEntity(usuario.nombre, usuario.email, Constantes.USUARIO)
            addUsuarioLogin(usuarioLogin)
        }
        return loginCorrecto
    }

    private suspend fun addUsuarioLogin(usuario: LoginEntity) = dao.addUsuarioLogin(usuario)

    suspend fun getUsuarioActual() = dao.getUsuarioActual()

    suspend fun cerrarSesion() = dao.cerrarSesion()
}