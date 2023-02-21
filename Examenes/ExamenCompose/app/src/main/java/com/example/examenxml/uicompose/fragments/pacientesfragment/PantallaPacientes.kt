package com.example.examenxml.uicompose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.examenxml.uicompose.fragments.pacientesfragment.PacienteEvent
import com.example.examenxml.uicompose.fragments.pacientesfragment.PacienteViewModel

@Composable
fun PantallaPacientes(
    navController: NavHostController,
    viewModel: PacienteViewModel = hiltViewModel()
) {
    viewModel.handleEvent(PacienteEvent.Eventos.LoadPacientes)
    val state by viewModel.uiPacienteState.collectAsState()

    LazyColumn {
        items(state.pacientes!!) { paciente ->
            PacienteCard(paciente = paciente, navController = navController)
        }
    }
}