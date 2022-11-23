package com.example.recyclerview.ui.addtarjeta

import com.example.recyclerview.domain.modelo.Tarjeta

sealed interface AddTarjetaEvent {
    data class AddTarjeta(val tarjeta: Tarjeta) : AddTarjetaEvent
}