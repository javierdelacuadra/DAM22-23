package com.example.recyclerview.ui.compose.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.data.repositories.PeliculasRepository
import com.example.recyclerview.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: PeliculasRepository
) : ViewModel() {

    private val _uiTrendingState: MutableStateFlow<TrendingState> by lazy {
        MutableStateFlow(TrendingState())
    }
    val uiTrendingState: StateFlow<TrendingState> = _uiTrendingState

    private val _uiError = Channel<String>()

    fun handleEvent(event: TrendingEvent.Eventos) {
        when (event) {
            TrendingEvent.Eventos.LoadPeliculas -> {
                cargarPeliculas()
            }
        }
    }

    private fun cargarPeliculas() {
        viewModelScope.launch {
            _uiTrendingState.update { it.copy(cargando = true) }
            repository.fetchTrendingMovies()
                .catch { cause -> _uiError.send(cause.message ?: "") }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiTrendingState.update {
                                it.copy(
                                    error = result.message,
                                    cargando = false
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            _uiTrendingState.update {
                                it.copy(
                                    movies = result.data ?: emptyList(),
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