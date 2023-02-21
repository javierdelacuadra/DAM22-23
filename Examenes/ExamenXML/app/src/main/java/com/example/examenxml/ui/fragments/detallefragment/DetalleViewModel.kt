package com.example.examenxml.ui.fragments.detallefragment

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
class DetalleViewModel @Inject constructor(
    private val repository: PacientesRepository
) : ViewModel() {

    private val _uiDetalleState: MutableStateFlow<DetalleEvent.DetalleState> by lazy {
        MutableStateFlow(DetalleEvent.DetalleState())
    }
    val uiDetalleState: StateFlow<DetalleEvent.DetalleState> = _uiDetalleState

    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun handleEvent(event: DetalleEvent) {
        when (event) {
            is DetalleEvent.LoadPaciente -> {
                cargarPaciente(event.id)
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