package com.example.formulariocompose.ui.pantallas.pantallaadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formulariocompose.domain.modelo.Persona
import com.example.formulariocompose.domain.usecases.AddPersonaUseCase
import com.example.formulariocompose.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val addPersonaUseCase: AddPersonaUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddState())
    val uiState: StateFlow<AddState> get() = _uiState

    fun handleEvent(event: AddEvent) {
        when (event) {
            is AddEvent.AddPersona -> addPersona(event.persona)
        }
    }

    private fun addPersona(persona: Persona): Boolean {
        if (persona.nombre.isEmpty() || persona.email.isEmpty() || persona.password.isEmpty()) {
            _uiState.value =
                AddState(mensaje = "Todos los campos son obligatorios")
            return false
        } else if (persona.email.count { it == ConstantesUI.ARROBA } > ConstantesUI.UNO) {
            _uiState.value =
                AddState(mensaje = "El email no puede contener más de un @")
            return false
        } else {
            viewModelScope.launch {
                try {
                    addPersonaUseCase.invoke(persona)
                    _uiState.value =
                        AddState(mensaje = "Persona guardada correctamente")
                } catch (e: Exception) {
                    _uiState.value =
                        AddState(mensaje = "Error al guardar la persona")
                }
            }
            return true
        }
    }
}