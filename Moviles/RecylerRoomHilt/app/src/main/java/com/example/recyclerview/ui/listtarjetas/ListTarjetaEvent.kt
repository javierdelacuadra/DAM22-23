package com.example.recyclerview.ui.listtarjetas

sealed interface ListTarjetaEvent {
    object GetTarjetas : ListTarjetaEvent
}