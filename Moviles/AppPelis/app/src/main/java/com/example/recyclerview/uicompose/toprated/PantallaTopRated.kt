package com.example.recyclerview.uicompose.toprated

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recyclerview.uicompose.pelisactivity.PeliculaCard

@Composable
fun PantallaTopRated(
    navController: NavHostController,
    viewModel: TopRatedViewModel = hiltViewModel()
) {
    viewModel.handleEvent(TopRatedEvent.Eventos.LoadPeliculas)
    val state by viewModel.uiTopRatedState.collectAsState()
    LazyColumn {
        items(state.movies) { pelicula ->
            PeliculaCard(pelicula = pelicula, navController = navController)
        }
    }
}