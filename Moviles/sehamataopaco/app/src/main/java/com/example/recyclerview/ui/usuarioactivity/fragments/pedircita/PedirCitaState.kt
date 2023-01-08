package com.example.recyclerview.ui.usuarioactivity.fragments.pedircita

import com.example.recyclerview.domain.modelo.Cita

data class PedirCitaState(
    val mensaje: String?,
    val listaEspecialidades: List<String>? = null,
    val listaDoctores: List<String>? = null,
    val listaFechas: List<String>? = null,
    val listaHoras: List<String>? = null,
    val cita: Cita? = null
)