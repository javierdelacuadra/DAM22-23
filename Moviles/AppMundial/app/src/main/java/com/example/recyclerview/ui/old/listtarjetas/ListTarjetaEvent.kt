package com.example.recyclerview.ui.old.listtarjetas

sealed interface ListTarjetaEvent {
    object GetTarjetas : ListTarjetaEvent
}