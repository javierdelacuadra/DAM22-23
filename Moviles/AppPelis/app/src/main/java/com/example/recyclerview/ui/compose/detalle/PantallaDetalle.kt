package com.example.recyclerview.ui.compose.detalle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.recyclerview.domain.modelo.IndividualMovie
import com.example.recyclerview.ui.common.ConstantesUI

@Composable
fun PantallaDetalle(id: Int) {
    val viewModel: DetalleViewModel = hiltViewModel()
    viewModel.handleEvent(DetalleEvent.LoadPelicula(id))
    val state by viewModel.uiDetalleState.collectAsState()
    val pelicula = state.pelicula
    if (pelicula != null) {
        IndividualMovieCard(pelicula)
    }
}

@Composable
fun IndividualMovieCard(pelicula: IndividualMovie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${pelicula.poster_path}",
                contentDescription = ConstantesUI.IMAGEN_DE_LA_PELICULA,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(292.dp)
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally)
            )
            pelicula.original_title?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h5
                )
            }
            pelicula.release_date?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h6,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = pelicula.revenue.toString() + ConstantesUI.DOLARES,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = pelicula.runtime.toString() + ConstantesUI.MINUTOS,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.body1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = pelicula.vote_average.toString() + ConstantesUI.DE_DIEZ,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = pelicula.vote_count.toString() + ConstantesUI.VOTOS,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}