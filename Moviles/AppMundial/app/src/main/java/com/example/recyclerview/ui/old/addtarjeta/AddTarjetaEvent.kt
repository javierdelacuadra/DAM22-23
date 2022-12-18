package com.example.recyclerview.ui.old.addtarjeta

sealed interface AddTarjetaEvent {
    data class AddTarjeta(
        val numeroTarjeta: String,
        val mes: String,
        val year: String,
        val cvv: String,
        val email: String
    ) : AddTarjetaEvent
}