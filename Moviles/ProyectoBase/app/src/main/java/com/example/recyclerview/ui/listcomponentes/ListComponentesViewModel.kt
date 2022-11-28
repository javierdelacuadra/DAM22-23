package com.example.recyclerview.ui.listcomponentes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.componentes.GetComponentesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListComponentesViewModel @Inject constructor(
    private val getComponentesUseCase: GetComponentesUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(ListComponentesState(null, emptyList()))
    val uiState: LiveData<ListComponentesState> get() = _uiState

    fun handleEvent(event: ListComponentesEvent) {
        when (event) {
            is ListComponentesEvent.GetComponentes -> loadComponentes(event.nombreEquipo)
        }
    }

    private fun loadComponentes(nombreEquipo: String) {
        viewModelScope.launch {
            try {
                _uiState.value =
                    _uiState.value?.copy(
                        lista = getComponentesUseCase.invoke(nombreEquipo)
                    )
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = "el equipo no existe")
            }
        }
    }
}