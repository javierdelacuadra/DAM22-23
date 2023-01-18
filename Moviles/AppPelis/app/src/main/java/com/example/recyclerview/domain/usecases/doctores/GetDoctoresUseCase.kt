package com.example.recyclerview.domain.usecases.doctores

import com.example.recyclerview.data.repositories.DoctoresRepository
import javax.inject.Inject

class GetDoctoresUseCase @Inject constructor(private val repository: DoctoresRepository) {
    suspend fun invoke() = repository.getDoctores()
}