package com.example.examenxml.ui.fragments.pacientesfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenxml.data.repositories.PacientesRepository
import com.example.examenxml.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PacienteViewModel @Inject constructor(
    private val pacientesRepository: PacientesRepository,
) : ViewModel() {

    private val _uiPacienteState: MutableStateFlow<PacienteEvent.PacienteState> by lazy {
        MutableStateFlow(PacienteEvent.PacienteState())
    }

    val uiPacienteState: StateFlow<PacienteEvent.PacienteState> =
        _uiPacienteState

    private val _uiError = Channel<String>()

    fun handleEvent(event: PacienteEvent.Eventos) {
        when (event) {
            is PacienteEvent.Eventos.LoadPacientes -> {
                cargarPacientes()
            }
        }
    }

    private fun cargarPacientes() {
        viewModelScope.launch {
            _uiPacienteState.update { it.copy(cargando = true) }
            pacientesRepository.fetchAllPacientes()
                .catch { _uiError.send("error inesperado") }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiPacienteState.update {
                                it.copy(
                                    error = result.message,
                                    cargando = false
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            _uiPacienteState.update {
                                it.copy(
                                    pacientes = result.data,
                                    cargando = false
                                )
                            }
                        }
                        else -> {}
                    }
                }
        }
    }
}