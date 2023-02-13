package com.example.recyclerview.uicompose.usuarioactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recyclerview.uicompose.ui.theme.FormularioTheme
import com.example.recyclerview.uicompose.usuarioactivity.fragments.pedircita.PantallaPedirCita
import com.example.recyclerview.uicompose.usuarioactivity.fragments.vercitas.PantallaVerCitas

class UsuarioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormularioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationUsuarioActivity()
                }
            }
        }
    }
}

@Composable
fun NavigationUsuarioActivity() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "pedircita"
    ) {
        composable("pedircita") {
            PantallaPedirCita()
        }
        composable("vercitas") {
            PantallaVerCitas()
        }
    }
}