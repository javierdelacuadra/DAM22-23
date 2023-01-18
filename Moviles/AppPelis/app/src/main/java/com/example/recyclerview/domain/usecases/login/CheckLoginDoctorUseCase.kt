package com.example.recyclerview.domain.usecases.login

import com.example.recyclerview.data.repositories.DoctoresRepository
import javax.inject.Inject

class CheckLoginDoctorUseCase @Inject constructor(private val repository: DoctoresRepository) {
    suspend fun invoke(nombre: String, password: String) = repository.checkLogin(nombre, password)
}