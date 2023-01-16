package com.example.recyclerview.ui.usuarioactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.login.CerrarSesionUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val cerrarSesion: CerrarSesionUseCase,
) : ViewModel() {
    private val _uiState = MutableLiveData(UsuarioState(null))
    val uiState: LiveData<UsuarioState> get() = _uiState

    fun handleEvent(event: UsuarioEvent) {
        when (event) {
            is UsuarioEvent.CerrarSesion -> cerrarSesion()
        }
    }

    private fun cerrarSesion() {
        viewModelScope.launch {
            try {
                cerrarSesion.invoke()
                _uiState.value =
                    _uiState.value?.copy(mensaje = ConstantesUI.SESION_CERRADA)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }
}