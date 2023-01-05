package com.example.recyclerview.domain.usecases.personas

import com.example.recyclerview.data.repositories.Repository
import javax.inject.Inject

class GetPersonasUseCase @Inject constructor(private val repository: Repository) {
    suspend fun invoke() = repository.getPersonas()
}