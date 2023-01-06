package com.example.recyclerview.ui.usuarioactivity.fragments.pedircita

data class PedirCitaState(
    val mensaje: String?,
    val listaEspecialidades: List<String> = ArrayList(),
    val listaDoctores: List<String> = emptyList(),
    val listaHoras: List<String> = emptyList(),
)