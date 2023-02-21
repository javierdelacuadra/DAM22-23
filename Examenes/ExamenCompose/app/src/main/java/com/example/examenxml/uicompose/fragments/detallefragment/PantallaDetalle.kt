package com.example.examenxml.uicompose.fragments.detallefragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examenxml.domain.modelo.Enfermedad
import com.example.examenxml.uicompose.PacienteCardWithoutNavigation

@Composable
fun PantallaDetalle(id: String) {
    val viewModel: DetalleViewModel = hiltViewModel()
    viewModel.handleEvent(DetalleEvent.LoadPaciente(id))
    val state by viewModel.uiDetalleState.collectAsState()
    val paciente = state.paciente
    val enfermedades = paciente?.enfermedades
    if (paciente != null) {
        PacienteCardWithoutNavigation(paciente = paciente)
    }

//    LazyColumn {
//        items(enfermedades!!) { enfermedad ->
//            EnfermedadCard(enfermedad = enfermedad)
//        }
//    }
}

@Composable
fun EnfermedadCard(enfermedad: Enfermedad) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(color = Color.LightGray)
        ) {
            Text(text = "Nombre: ${enfermedad.nombre}", modifier = Modifier.padding(8.dp))
        }
    }
}