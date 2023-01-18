package com.example.recyclerview.ui.peliculasactivity.fragments.toprated

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
class TopRatedViewModel @Inject constructor(
    private val repository: PeliculasRepository
) : ViewModel() {
    private val _uiTopRatedState: MutableStateFlow<TopRatedEvent.TopRatedState> by lazy {
        MutableStateFlow(TopRatedEvent.TopRatedState())
    }
    val uiTopRatedState: StateFlow<TopRatedEvent.TopRatedState> = _uiTopRatedState

    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun handleEvent(event: TopRatedEvent.Eventos) {
        when (event) {
            TopRatedEvent.Eventos.LoadPeliculas -> {
                cargarPeliculasTopRated()
            }
        }
    }

    private fun cargarPeliculasTopRated() {
        viewModelScope.launch {
            _uiTopRatedState.update { it.copy(cargando = true) }
            repository.fetchTopRatedMovies()
                .catch { cause -> _uiError.send(cause.message ?: "") }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiTopRatedState.update {
                                it.copy(
                                    error = result.message,
                                    cargando = false
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            _uiTopRatedState.update {
                                it.copy(
                                    movies = result.data,
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