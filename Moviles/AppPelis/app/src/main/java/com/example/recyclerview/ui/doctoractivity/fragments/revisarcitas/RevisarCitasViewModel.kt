package com.example.recyclerview.ui.doctoractivity.fragments.revisarcitas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.modelo.Cita
import com.example.recyclerview.domain.usecases.citas.GetCitasByDoctorUseCase
import com.example.recyclerview.domain.usecases.citas.MarcarCitaUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RevisarCitasViewModel @Inject constructor(
    private val getCitas: GetCitasByDoctorUseCase,
    private val marcarCita: MarcarCitaUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(RevisarCitasState(null, emptyList()))
    val uiState: LiveData<RevisarCitasState> get() = _uiState

    fun handleEvent(event: RevisarCitasEvent) {
        when (event) {
            is RevisarCitasEvent.GetCitas -> cargarCitas()
            is RevisarCitasEvent.MarcarComoRealizada -> marcarCita(event.cita)
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

    private fun marcarCita(cita: Cita) {
        viewModelScope.launch {
            try {
                marcarCita.invoke(cita)
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.CITA_CONFIRMADA)
                val citas = _uiState.value?.citas
                val nuevaLista = citas?.filter { it.id != cita.id }
                _uiState.value = nuevaLista?.let { _uiState.value?.copy(citas = it) }
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CANCELAR_CITA)
            }
        }
    }
}