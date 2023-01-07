package com.example.recyclerview.domain.usecases.citas

import com.example.recyclerview.data.repositories.CitasRepository
import com.example.recyclerview.data.repositories.UsuariosRepository
import com.example.recyclerview.domain.modelo.Cita
import javax.inject.Inject

class GetCitasByDoctorUseCase @Inject constructor(
    private val repository: CitasRepository,
    private val usuariosRepository: UsuariosRepository
) {
    suspend fun invoke(): List<Cita> {
        val email = usuariosRepository.getUsuarioActual().email
        return repository.getCitasByDoctorEmail(email)
    }
}