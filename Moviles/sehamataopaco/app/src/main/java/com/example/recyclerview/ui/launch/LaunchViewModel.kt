package com.example.recyclerview.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.doctores.GetDoctoresUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val getDoctores: GetDoctoresUseCase,
) : ViewModel() {
    private val _uiState = MutableLiveData(LaunchState(null, emptyList()))
    val uiState: LiveData<LaunchState> get() = _uiState

    fun handleEvent(event: LaunchEvent) {
        when (event) {
            is LaunchEvent.GetDoctores -> cargarDoctores()
        }
    }

    private fun cargarDoctores() {
        viewModelScope.launch {
            try {
                _uiState.value =
                    _uiState.value?.copy(lista = getDoctores.invoke())
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }
}