package com.example.recyclerview.domain.usecases.doctores

import javax.inject.Inject

class GetDoctoresByEspecialidadUseCase @Inject constructor(
    private val getDoctores: GetDoctoresUseCase
) {
    suspend operator fun invoke(especialidad: String): List<String> {
        return getDoctores.invoke().filter { it.especialidad == especialidad }
            .map { it.nombre }
    }
}