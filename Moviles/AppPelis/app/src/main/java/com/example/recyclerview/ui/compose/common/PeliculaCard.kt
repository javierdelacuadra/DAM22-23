package com.example.recyclerview.ui.compose.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recyclerview.domain.modelo.Pelicula
import com.example.recyclerview.ui.common.ConstantesUI

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
                text = ConstantesUI.FECHA_DE_ESTRENO + pelicula.release_date,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = ConstantesUI.ESTRELLA + pelicula.vote_average.toString() + ConstantesUI.DE_DIEZ,
                style = MaterialTheme.typography.body1
            )
        }
    }
}