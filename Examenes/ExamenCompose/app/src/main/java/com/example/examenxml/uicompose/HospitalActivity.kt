package com.example.examenxml.uicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recyclerview.ui.compose.theme.FormularioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HospitalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormularioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.surface
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = currentRoute(navController)
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                NavigationPeliculasActivity(navController)
            }
        },
        bottomBar = {
            if (currentRoute == "hospitales" || currentRoute == "pacientes") {
                BottomNavigation {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Rounded.FavoriteBorder, contentDescription = null) },
                        label = { Text("Hospitales") },
                        selected = currentRoute == "hospitales",
                        onClick = {
                            navController.navigate("hospitales")
                        }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Star, contentDescription = null) },
                        label = { Text("Pacientes") },
                        selected = currentRoute == "pacientes",
                        onClick = {
                            navController.navigate("pacientes")
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun currentRoute(navController: NavController): String {
    val backStackEntry = navController.currentBackStackEntryAsState()
    return backStackEntry.value?.destination?.route ?: ""
}

@Composable
fun NavigationPeliculasActivity(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "hospitales"
    ) {
        composable("hospitales") {
            PantallaHospitales(navController)
        }
        composable("pacientes") {
            PantallaPacientes(navController)
        }
        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            requireNotNull(id)
            PantallaDetalle(id)
        }
    }
}