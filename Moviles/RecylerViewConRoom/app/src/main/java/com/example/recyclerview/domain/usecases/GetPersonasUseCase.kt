package com.example.recyclerview.domain.usecases

import com.example.recyclerview.data.Repository

class GetPersonasUseCase (private val repository: Repository) {
    suspend fun invoke() = repository.getPersonas()
}