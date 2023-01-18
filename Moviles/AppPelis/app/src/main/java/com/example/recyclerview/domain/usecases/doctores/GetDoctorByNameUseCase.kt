package com.example.recyclerview.domain.usecases.doctores

import com.example.recyclerview.data.repositories.DoctoresRepository
import javax.inject.Inject

class GetDoctorByNameUseCase @Inject constructor(private val repository: DoctoresRepository) {
    suspend fun invoke(nombre: String) = repository.getDoctorByName(nombre)
}