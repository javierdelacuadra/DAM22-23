package com.example.recyclerview.ui.splashscreen

sealed interface SplashEvent {
    object checkActualUser : SplashEvent
}