package com.example.recyclerview.domain.usecases.doctores

import com.example.recyclerview.data.repositories.CitasRepository
import com.example.recyclerview.data.repositories.DoctoresRepository
import javax.inject.Inject

class GetHoursUseCase @Inject constructor(
    private val repository: CitasRepository,
    private val repositoryDoctores: DoctoresRepository
) {
    suspend fun invoke(fecha: String, nombreDoctor: String): MutableList<String> {
        val emailDoctor = repositoryDoctores.getDoctorByName(nombreDoctor).email
        val citas = repository.getCitasByDoctorEmail(emailDoctor)
        val availableHours = mutableListOf<String>()
        for (hour in 9..13) {
            availableHours.add(hour.toString())
        }
        availableHours.forEachIndexed { index, s ->
            availableHours[index] = "$s:00"
        }
        availableHours.removeAll(citas.filter { it.fecha == fecha }.map { it.hora })
        return availableHours
    }
}