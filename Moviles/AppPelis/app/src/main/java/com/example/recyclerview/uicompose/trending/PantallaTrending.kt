package com.example.recyclerview.uicompose.trending

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recyclerview.uicompose.pelisactivity.PeliculaCard

@Composable
fun PantallaTrending(
    navController: NavHostController,
    viewModel: TrendingViewModel = hiltViewModel()
) {
    viewModel.handleEvent(TrendingEvent.Eventos.LoadPeliculas)
    val state by viewModel.uiTrendingState.collectAsState()
    LazyColumn {
        items(state.movies) { pelicula ->
            PeliculaCard(pelicula = pelicula, navController = navController)
        }
    }
}