package com.example.recyclerview.ui.usuarioactivity.fragments.pedircita

data class PedirCitaState(
    val mensaje: String?,
    val listaEspecialidades: List<String>? = null,
    val listaDoctores: List<String>? = null,
    val listaFechas: List<String>? = null,
    val listaHoras: List<String>? = null,
)