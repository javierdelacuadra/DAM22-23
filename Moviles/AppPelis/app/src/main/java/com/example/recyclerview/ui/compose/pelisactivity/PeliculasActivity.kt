package com.example.recyclerview.ui.compose.pelisactivity

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
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.ui.compose.detalle.PantallaDetalle
import com.example.recyclerview.ui.compose.theme.FormularioTheme
import com.example.recyclerview.ui.compose.toprated.PantallaTopRated
import com.example.recyclerview.ui.compose.trending.PantallaTrending
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeliculasActivity : ComponentActivity() {
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
            if (currentRoute == ConstantesUI.TRENDING || currentRoute == ConstantesUI.TOP_RATED_CAMEL_CASE) {
                BottomNavigation {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Rounded.FavoriteBorder, contentDescription = null) },
                        label = { Text(ConstantesUI.TRENDING_UPPER_CASE) },
                        selected = currentRoute == ConstantesUI.TRENDING,
                        onClick = {
                            navController.navigate(ConstantesUI.TRENDING)
                        }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Star, contentDescription = null) },
                        label = { Text(ConstantesUI.TOP_RATED) },
                        selected = currentRoute == ConstantesUI.TOP_RATED_CAMEL_CASE,
                        onClick = {
                            navController.navigate(ConstantesUI.TOP_RATED_CAMEL_CASE)
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
        startDestination = ConstantesUI.TRENDING
    ) {
        composable(ConstantesUI.TRENDING) {
            PantallaTrending(navController)
        }
        composable(ConstantesUI.TOP_RATED_CAMEL_CASE) {
            PantallaTopRated(navController)
        }
        composable(
            route = ConstantesUI.DETALLE_ROUTE,
            arguments = listOf(navArgument(ConstantesUI.ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(ConstantesUI.ID)
            requireNotNull(id)
            PantallaDetalle(id)
        }
    }
}