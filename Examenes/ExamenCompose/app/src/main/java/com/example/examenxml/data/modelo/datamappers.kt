package com.example.examenxml.data.modelo

import com.example.examenxml.domain.modelo.Paciente

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