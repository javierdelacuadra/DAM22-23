package com.example.recyclerview.ui.fragments.pedircita

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.doctores.GetDoctoresUseCase
import com.example.recyclerview.domain.usecases.doctores.GetEspecialidadesUseCase
import com.example.recyclerview.domain.usecases.doctores.GetHoursUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import kotlinx.coroutines.launch
import javax.inject.Inject

class PedirCitaViewModel @Inject constructor(
    private val getEspecialidades: GetEspecialidadesUseCase,
    private val getDoctores: GetDoctoresUseCase,
    private val getHoras: GetHoursUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(PedirCitaState(null))
    val uiState: LiveData<PedirCitaState> get() = _uiState

    fun handleEvent(event: PedirCitaEvent) {
        when (event) {
            is PedirCitaEvent.GetEspecialidades -> cargarEspecialidades()
            is PedirCitaEvent.GetDoctores -> cargarDoctores(event.selectedItem)
            is PedirCitaEvent.GetHours -> cargarHoras(event.nombreDoctor)
        }
    }

    private fun cargarDoctores(selectedItem: String) {
        viewModelScope.launch {
            val listaDoctores = getDoctores.invoke().filter { it.especialidad == selectedItem }
                .map { it.nombre }
            _uiState.value = _uiState.value?.copy(listaDoctores = listaDoctores)
        }
    }

    private fun cargarEspecialidades() {
        viewModelScope.launch {
            try {
                _uiState.value =
                    _uiState.value?.copy(listaEspecialidades = getEspecialidades.invoke())
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }

    private fun cargarHoras(nombreDoctor: String) {
        viewModelScope.launch {
            try {
                _uiState.value =
                    _uiState.value?.copy(listaHoras = getHoras.invoke(nombreDoctor))
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }
}