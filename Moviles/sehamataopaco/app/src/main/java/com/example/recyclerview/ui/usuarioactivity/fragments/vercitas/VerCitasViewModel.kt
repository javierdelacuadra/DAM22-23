package com.example.recyclerview.ui.usuarioactivity.fragments.vercitas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.modelo.Cita
import com.example.recyclerview.domain.usecases.citas.CancelarCitaUseCase
import com.example.recyclerview.domain.usecases.citas.GetCitasUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerCitasViewModel @Inject constructor(
    private val getCitas: GetCitasUseCase,
    private val cancelarCita: CancelarCitaUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(VerCitasState(null, emptyList()))
    val uiState: LiveData<VerCitasState> get() = _uiState

    fun handleEvent(event: VerCitasEvent) {
        when (event) {
            is VerCitasEvent.GetCitas -> cargarCitas()
            is VerCitasEvent.CancelarCita -> cancelarCita(event.cita)
        }
    }

    private fun cargarCitas() {
        viewModelScope.launch {
            val listaCitas = getCitas.invoke()
            try {
                _uiState.value = _uiState.value?.copy(citas = listaCitas)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }

    private fun cancelarCita(cita: Cita) {
        viewModelScope.launch {
            try {
                cancelarCita.invoke(cita)
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.CITA_CANCELADA)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CANCELAR_CITA)
            }
        }
    }
}