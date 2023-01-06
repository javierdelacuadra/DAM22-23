package com.example.recyclerview.data.modelo

import com.example.recyclerview.domain.modelo.*

fun PersonaEntity.toPersona(): Persona {
    return Persona(
        this.nombre,
        this.password,
        this.email,
    )
}

fun Persona.toPersonaEntity(): PersonaEntity {
    return PersonaEntity(
        this.nombre,
        this.password,
        this.email,
    )
}

fun TarjetaEntity.toTarjeta(): Tarjeta {
    return Tarjeta(
        this.numeroTarjeta,
        this.fechaCaducidad,
        this.cvv,
        this.email,
    )
}

fun Tarjeta.toTarjetaEntity(): TarjetaEntity {
    return TarjetaEntity(
        this.numeroTarjeta,
        this.fechaCaducidad,
        this.cvv,
        this.email,
    )
}

fun PersonaWithTarjetas.toPersona(): Persona {
    return Persona(
        this.persona.nombre,
        this.persona.password,
        this.persona.email,
        this.tarjeta?.map { it.toTarjeta() },
    )
}

//parte 2

fun CitaEntity.toCita(): Cita {
    return Cita(
        this.id,
        this.fecha,
        this.hora,
        this.emailUsuario,
        this.emailDoctor,
        this.realizada,
    )
}

fun Cita.toCitaEntity(): CitaEntity {
    return CitaEntity(
        this.id,
        this.fecha,
        this.hora,
        this.emailUsuario,
        this.emailDoctor,
        this.realizada,
    )
}

fun DoctorEntity.toDoctor(): Doctor {
    return Doctor(
        this.nombre,
        this.especialidad,
        this.email,
        this.fecha,
    )
}

fun Doctor.toDoctorEntity(): DoctorEntity {
    return DoctorEntity(
        this.nombre,
        this.especialidad,
        this.email,
        this.fecha,
    )
}

fun DoctorWithHospitales.toDoctor(): Doctor {
    return Doctor(
        this.doctor.nombre,
        this.doctor.especialidad,
        this.doctor.email,
        this.doctor.fecha,
        emptyList(),
        this.hospitales.map { it.toHospital() },
    )
}

fun HospitalEntity.toHospital(): Hospital {
    return Hospital(
        this.nombre,
        this.direccion,
        this.telefono,
    )
}

fun DoctorWithCitas.toDoctor(): Doctor {
    return Doctor(
        this.doctor.nombre,
        this.doctor.especialidad,
        this.doctor.email,
        this.doctor.fecha,
        this.citas.map { it.toCita() },
        emptyList(),
    )
}

fun HospitalWithDoctores.toHospital(): Hospital {
    return Hospital(
        this.hospital.nombre,
        this.hospital.direccion,
        this.hospital.telefono,
        this.doctores.map { it.toDoctor() },
    )
}

fun Hospital.toHospitalEntity(): HospitalEntity {
    return HospitalEntity(
        this.nombre,
        this.direccion,
        this.telefono,
    )
}

fun UsuarioEntity.toUsuario(): Usuario {
    return Usuario(
        this.nombre,
        this.password,
        this.email,
        this.telefono,
        this.fecha
    )
}

fun Usuario.toUsuarioEntity(): UsuarioEntity {
    return UsuarioEntity(
        this.nombre,
        this.password,
        this.email,
        this.telefono,
        this.fecha
    )
}

fun UsuarioWithCitas.toUsuario(): Usuario {
    return Usuario(
        this.usuario.nombre,
        this.usuario.password,
        this.usuario.email,
        this.usuario.telefono,
        this.usuario.fecha,
        this.citas.map { it.toCita() },
    )
}