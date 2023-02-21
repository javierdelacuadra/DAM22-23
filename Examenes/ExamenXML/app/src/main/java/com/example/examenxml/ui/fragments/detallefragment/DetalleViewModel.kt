package com.example.examenxml.ui.fragments.detallefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenxml.data.modelo.EnfermedadEntity
import com.example.examenxml.data.modelo.toEnfermedad
import com.example.examenxml.data.repositories.PacientesRepository
import com.example.examenxml.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleViewModel @Inject constructor(
    private val repository: PacientesRepository
) : ViewModel() {

    private val _uiDetalleState: MutableStateFlow<DetalleEvent.DetalleState> by lazy {
        MutableStateFlow(DetalleEvent.DetalleState())
    }
    val uiDetalleState: StateFlow<DetalleEvent.DetalleState> = _uiDetalleState

    private val _uiError = Channel<String>()

    fun handleEvent(event: DetalleEvent) {
        when (event) {
            is DetalleEvent.LoadPaciente -> {
                cargarPaciente(event.id)
            }
            is DetalleEvent.AddEnfermedad -> {
                addEnfermedad(event.enfermedadEntity)
            }
            is DetalleEvent.EditarPaciente -> {
                editarPaciente(event.id, event.nombre)
            }
        }
    }

    private fun editarPaciente(id: String, nombre: String) {
        viewModelScope.launch {
            repository.editarPaciente(id, nombre)
                .catch { cause -> _uiError.send(cause.message ?: "") }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiDetalleState.update {
                                it.copy(
                                    mensaje = result.message,
                                    cargando = false
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            val paciente = _uiDetalleState.value.paciente
                            paciente?.paciente?.nombre = nombre
                            _uiDetalleState.update {
                                it.copy(
                                    paciente = paciente,
                                    cargando = false
                                )
                            }
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun addEnfermedad(enfermedadEntity: EnfermedadEntity) {
        viewModelScope.launch {
            repository.addEnfermedad(enfermedadEntity)
                .catch { cause -> _uiError.send(cause.message ?: "") }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiDetalleState.update {
                                it.copy(
                                    mensaje = result.message,
                                    cargando = false
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            val paciente = _uiDetalleState.value.paciente
                            paciente?.enfermedades?.add(enfermedadEntity)
                            _uiDetalleState.update {
                                it.copy(
                                    paciente = paciente,
                                    cargando = false
                                )
                            }
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun cargarPaciente(id: String) {
        viewModelScope.launch {
            _uiDetalleState.update { it.copy(cargando = true) }
            repository.fetchPacienteByID(id)
                .catch { cause -> _uiError.send(cause.message ?: "") }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiDetalleState.update {
                                it.copy(
                                    mensaje = result.message,
                                    cargando = false
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            _uiDetalleState.update {
                                it.copy(
                                    paciente = result.data,
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