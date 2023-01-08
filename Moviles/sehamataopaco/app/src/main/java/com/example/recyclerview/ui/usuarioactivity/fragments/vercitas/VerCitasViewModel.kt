package com.example.recyclerview.ui.usuarioactivity.fragments.vercitas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.citas.GetCitasUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerCitasViewModel @Inject constructor(
    private val getCitas: GetCitasUseCase,
) : ViewModel() {
    private val _uiState = MutableLiveData(VerCitasState(null, emptyList()))
    val uiState: LiveData<VerCitasState> get() = _uiState

    fun handleEvent(event: VerCitasEvent) {
        when (event) {
            is VerCitasEvent.GetCitas -> cargarCitas()
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
}