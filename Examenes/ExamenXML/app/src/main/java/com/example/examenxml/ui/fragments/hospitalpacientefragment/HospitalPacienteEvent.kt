package com.example.examenxml.ui.fragments.hospitalpacientefragment

import com.example.examenxml.domain.modelo.Hospital
import com.example.examenxml.domain.modelo.Paciente
import java.util.*

sealed interface HospitalPacienteEvent {
    sealed class Eventos {
        object LoadHospitales : Eventos()
        data class LoadPacientes(val id: String) : Eventos()
        data class BorrarHospital(val id: String) : Eventos()
    }

    data class HospitalPacienteState(
        val hospitales: List<Hospital>? = emptyList(),
        val pacientes: List<Paciente>? = emptyList(),
        val cargando: Boolean = false,
        val error: String? = null,
    )
}