package com.example.recyclerview.uicompose.loginactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recyclerview.uicompose.loginactivity.fragments.logindoctor.PantallaLoginDoctor
import com.example.recyclerview.uicompose.loginactivity.fragments.loginusuario.PantallaLoginUsuario
import com.example.recyclerview.uicompose.ui.theme.FormularioTheme
import com.example.recyclerview.uicompose.usuarioactivity.fragments.pedircita.PantallaPedirCita
import com.example.recyclerview.uicompose.usuarioactivity.fragments.vercitas.PantallaVerCitas

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormularioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationLoginActivity()
                }
            }
        }
    }
}

@Composable
fun NavigationLoginActivity() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "loginUsuario"
    ) {
        composable("loginUsuario") {
            PantallaLoginUsuario()
        }
        composable("loginDoctor") {
            PantallaLoginDoctor()
        }
    }
}