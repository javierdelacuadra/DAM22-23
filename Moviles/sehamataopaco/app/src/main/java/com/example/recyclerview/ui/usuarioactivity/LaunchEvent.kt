package com.example.recyclerview.ui.usuarioactivity

sealed interface LaunchEvent {
    object GetDoctores : LaunchEvent
}
