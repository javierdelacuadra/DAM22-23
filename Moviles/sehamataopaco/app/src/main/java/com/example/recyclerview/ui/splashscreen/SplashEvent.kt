package com.example.recyclerview.ui.splashscreen

sealed interface SplashEvent {
    object CheckActualUser : SplashEvent
}