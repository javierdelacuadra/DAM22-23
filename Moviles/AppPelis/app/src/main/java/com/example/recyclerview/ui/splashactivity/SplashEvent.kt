package com.example.recyclerview.ui.splashactivity

sealed interface SplashEvent {
    object CheckActualUser : SplashEvent
}