package com.example.recyclerview.ui.listcomponentes

sealed interface ListComponentesEvent {
    data class GetComponentes(val nombreEquipo: String) : ListComponentesEvent
}