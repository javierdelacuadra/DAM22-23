package com.example.recyclerview.uicompose.pelisactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recyclerview.domain.modelo.Pelicula
import com.example.recyclerview.uicompose.detalle.PantallaDetalle
import com.example.recyclerview.uicompose.theme.FormularioTheme
import com.example.recyclerview.uicompose.toprated.PantallaTopRated
import com.example.recyclerview.uicompose.trending.PantallaTrending
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
            if (currentRoute == "trending" || currentRoute == "top_rated") {
                BottomNavigation {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Rounded.FavoriteBorder, contentDescription = null) },
                        label = { Text("Trending") },
                        selected = currentRoute == "trending",
                        onClick = {
                            navController.navigate("trending")
                        }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Star, contentDescription = null) },
                        label = { Text("Top Rated") },
                        selected = currentRoute == "top_rated",
                        onClick = {
                            navController.navigate("top_rated")
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
        startDestination = "trending"
    ) {
        composable("trending") {
            PantallaTrending(navController)
        }
        composable("top_rated") {
            PantallaTopRated(navController)
        }
        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            requireNotNull(id)
            PantallaDetalle(id)
        }
    }
}

@Composable
fun PeliculaCard(
    pelicula: Pelicula,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .clickable { navController.navigate("detalle/${pelicula.id}") },
        ) {
            Text(
                text = pelicula.title,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Fecha de estreno: " + pelicula.release_date,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "\u2605" + pelicula.vote_average.toString() + "/10",
                style = MaterialTheme.typography.body1
            )
        }
    }
}