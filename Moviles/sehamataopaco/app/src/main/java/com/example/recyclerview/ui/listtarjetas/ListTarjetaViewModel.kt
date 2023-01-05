package com.example.recyclerview.ui.listtarjetas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.tarjetas.GetTarjetasUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTarjetaViewModel @Inject constructor(
    private val getTarjetasUseCase: GetTarjetasUseCase,
) : ViewModel() {
    private val _uiState = MutableLiveData(ListTarjetaState(null, emptyList()))
    val uiState: LiveData<ListTarjetaState> get() = _uiState

    fun handleEvent(event: ListTarjetaEvent) {
        when (event) {
            is ListTarjetaEvent.GetTarjetas -> cargarTarjetas()
        }
    }

    private fun cargarTarjetas() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(lista = getTarjetasUseCase.invoke())
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_TARJETAS)
            }
        }
    }
}