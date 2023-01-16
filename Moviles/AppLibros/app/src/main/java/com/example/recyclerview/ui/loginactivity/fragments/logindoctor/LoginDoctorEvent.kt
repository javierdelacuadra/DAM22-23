package com.example.recyclerview.ui.loginactivity.fragments.logindoctor

sealed interface LoginDoctorEvent {
    data class Login(val nombre: String, val email: String) : LoginDoctorEvent
}