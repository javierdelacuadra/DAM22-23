package com.example.recyclerview.domain.usecases.doctores

import com.example.recyclerview.data.repositories.CitasRepository
import com.example.recyclerview.data.repositories.DoctoresRepository
import com.example.recyclerview.domain.modelo.Cita
import java.time.LocalDate
import javax.inject.Inject

class GetFechasUseCase @Inject constructor(
    private val repository: CitasRepository,
    private val repositoryDoctores: DoctoresRepository
) {
    suspend fun invoke(nombreDoctor: String): MutableList<String> {
        val emailDoctor = repositoryDoctores.getDoctorByName(nombreDoctor).email
        val citas = repository.getCitasByDoctorEmail(emailDoctor)
        val availableDates = mutableListOf<String>()
        for (date in generateDates()) {
            if (checkAvailability(citas, date)) {
                availableDates.add(date.toString())
            }
        }
        return availableDates
    }

    private fun generateDates(): List<LocalDate> {
        val currentDate = LocalDate.now()
        val dates = mutableListOf<LocalDate>()
        for (i in 1..5) {
            dates.add(currentDate.plusDays(i.toLong()))
        }
        return dates
    }

    private fun checkAvailability(citas: List<Cita>, date: LocalDate): Boolean {
        val citasOfTheDay = citas.filter { it.fecha == date.toString() }
        return citasOfTheDay.size < 5
    }
}