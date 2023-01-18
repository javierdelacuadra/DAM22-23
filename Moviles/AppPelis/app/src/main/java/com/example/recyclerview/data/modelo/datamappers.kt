package com.example.recyclerview.data.modelo

import com.example.recyclerview.domain.modelo.*

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

fun PeliculaEntity.toPelicula(): Pelicula {
    return Pelicula(
        this.id,
        this.title,
        this.vote_count,
        this.vote_average,
        this.release_date,
        this.popularity,
        this.overview,
        this.poster_path,
    )
}

fun MovieResponse.toIndividualMovie(): IndividualMovie {
    return IndividualMovie(
        this.poster_path,
        this.id,
        this.original_title,
        this.overview,
        this.release_date,
        this.revenue,
        this.runtime,
        this.vote_average,
        this.vote_count,
    )
}