package com.example.examenxml.uicompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavHostController
import com.example.examenxml.domain.modelo.Hospital
import com.example.examenxml.domain.modelo.Paciente
import com.example.examenxml.uicompose.fragments.hospitalpacientefragment.HospitalPacienteEvent
import com.example.examenxml.uicompose.fragments.hospitalpacientefragment.HospitalPacienteViewModel

@Composable
fun PantallaHospitales(
    navController: NavHostController,
    viewModel: HospitalPacienteViewModel = hiltViewModel()
) {
    viewModel.handleEvent(HospitalPacienteEvent.Eventos.LoadHospitales)
    val state by viewModel.uiHospitalPacienteState.collectAsState()

    LazyColumn {
        items(state.hospitales) { hospital ->
            HospitalCard(hospital = hospital, viewModel = viewModel)
        }
    }

    state.pacientes.let {
        if (it.isNotEmpty()) {
            LazyColumn {
                items(it) { paciente ->
                    PacienteCard(paciente = paciente, navController = navController)
                }
            }
        }
    }
}

@Composable
fun HospitalCard(hospital: Hospital, viewModel: HospitalPacienteViewModel = hiltViewModel()) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(color = Color.LightGray)
                .clickable { viewModel.handleEvent(HospitalPacienteEvent.Eventos.LoadPacientes(hospital.id)) }
        ) {
            Text(text = "UUID: ${hospital.id}", modifier = Modifier.padding(8.dp))
            Text(text = "Nombre: ${hospital.nombre}", modifier = Modifier.padding(8.dp))
            Text(text = "Camas: ${hospital.numeroCamas}", modifier = Modifier.padding(8.dp))
            Text(text = "Direccion: ${hospital.direccion}", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun PacienteCard(paciente: Paciente, navController: NavHostController) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(color = Color.LightGray)
                .clickable { navController.navigate("detalle/${paciente.id}") }
        ) {
            Text(text = "UUID: ${paciente.id}", modifier = Modifier.padding(8.dp))
            Text(text = "Nombre: ${paciente.nombre}", modifier = Modifier.padding(8.dp))
            Text(text = "Dni: ${paciente.dni}", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun PacienteCardWithoutNavigation(paciente: Paciente) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(color = Color.LightGray)
        ) {
            Text(text = "UUID: ${paciente.id}", modifier = Modifier.padding(8.dp))
            Text(text = "Nombre: ${paciente.nombre}", modifier = Modifier.padding(8.dp))
            Text(text = "Dni: ${paciente.dni}", modifier = Modifier.padding(8.dp))
        }
    }
}