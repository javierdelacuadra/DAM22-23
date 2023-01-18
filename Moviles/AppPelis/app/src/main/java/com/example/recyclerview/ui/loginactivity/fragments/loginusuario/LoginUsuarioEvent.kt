package com.example.recyclerview.ui.loginactivity.fragments.loginusuario

sealed interface LoginUsuarioEvent {
    data class Login(val email: String, val password: String) : LoginUsuarioEvent
}