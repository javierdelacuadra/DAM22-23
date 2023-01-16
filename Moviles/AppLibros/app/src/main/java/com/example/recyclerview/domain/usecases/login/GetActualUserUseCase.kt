package com.example.recyclerview.domain.usecases.login

import com.example.recyclerview.data.repositories.UsuariosRepository
import javax.inject.Inject

class GetActualUserUseCase @Inject constructor(private val repository: UsuariosRepository) {
    suspend fun invoke() = repository.getUsuarioActual()
}