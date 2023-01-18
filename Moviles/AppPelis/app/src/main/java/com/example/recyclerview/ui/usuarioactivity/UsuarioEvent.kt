package com.example.recyclerview.ui.usuarioactivity

sealed interface UsuarioEvent {
    object CerrarSesion : UsuarioEvent
}
