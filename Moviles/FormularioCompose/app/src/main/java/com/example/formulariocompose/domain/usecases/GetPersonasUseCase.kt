package com.example.formulariocompose.domain.usecases

import com.example.formulariocompose.data.Repository
import javax.inject.Inject

class GetPersonasUseCase @Inject constructor(private val repository: Repository) {
    suspend fun invoke() = repository.getPersonas()
}