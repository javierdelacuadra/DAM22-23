package com.example.recyclerview.domain.usecases.login

import com.example.recyclerview.data.repositories.UsuariosRepository
import javax.inject.Inject

class CheckLoginUsuarioUseCase @Inject constructor(private val repository: UsuariosRepository) {
    suspend fun invoke(nombre: String, password: String) = repository.checkLogin(nombre, password)
}