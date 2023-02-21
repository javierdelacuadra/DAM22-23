package com.example.examenxml.data.modelo

import com.example.examenxml.domain.modelo.Enfermedad
import com.example.examenxml.domain.modelo.Paciente
import java.util.*

fun PacienteEntity.toPaciente() = Paciente(
    id = id,
    nombre = nombre,
    dni = dni,
)

fun Paciente.toPacienteEntity() = PacienteEntity(
    id = id,
    nombre = nombre,
    dni = dni,
)

fun PacienteEntity.toPacienteWithEnfermedades(enfermedades: List<EnfermedadEntity>) = Paciente(
    id = id,
    nombre = nombre,
    dni = dni,
    enfermedades = enfermedades.map { it.toEnfermedad() }.toMutableList()
)

fun EnfermedadEntity.toEnfermedad() = Enfermedad(
    nombre = nombre
)

fun Enfermedad.toEnfermedadEntity(pacienteId: UUID) = EnfermedadEntity(
    nombre = nombre,
    pacienteId = pacienteId
)