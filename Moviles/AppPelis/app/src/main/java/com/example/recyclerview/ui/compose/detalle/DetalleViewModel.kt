package com.example.recyclerview.ui.compose.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.data.repositories.PeliculasRepository
import com.example.recyclerview.network.NetworkResult
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleViewModel @Inject constructor(
    private val repository: PeliculasRepository
) : ViewModel() {

    private val _uiDetalleState: MutableStateFlow<DetalleEvent.DetalleState> by lazy {
        MutableStateFlow(DetalleEvent.DetalleState())
    }
    val uiDetalleState: StateFlow<DetalleEvent.DetalleState> = _uiDetalleState

    private val _uiError = Channel<String>()

    fun handleEvent(event: DetalleEvent) {
        when (event) {
            is DetalleEvent.LoadPelicula -> {
                cargarPeliculas(event.id)
            }
        }
    }

    private fun cargarPeliculas(id: Int) {
        viewModelScope.launch {
            _uiDetalleState.update { it.copy(cargando = true) }
            repository.fetchPeliculaByID(id)
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
                                    pelicula = result.data,
                                    cargando = false
                                )
                            }
                        }
                        else -> {
                            _uiDetalleState.update {
                                it.copy(
                                    mensaje = ConstantesUI.ERROR_DESCONOCIDO,
                                    cargando = false
                                )
                            }
                        }
                    }
                }
        }
    }
}