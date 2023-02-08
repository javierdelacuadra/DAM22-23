package com.example.formulariocompose.ui.pantallas.pantallalist

import androidx.lifecycle.*
import com.example.formulariocompose.domain.modelo.Persona
import com.example.formulariocompose.domain.usecases.DeletePersonaUseCase
import com.example.formulariocompose.domain.usecases.GetPersonasUseCase
import com.example.formulariocompose.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getPersonasUseCase: GetPersonasUseCase,
    private val deletePersonaUseCase: DeletePersonaUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListState(null, emptyList()))
    val uiState: StateFlow<ListState> get() = _uiState

    fun handleEvent(event: ListEvent) {
        when (event) {
            is ListEvent.GetPersonas -> cargarPersonas()
            is ListEvent.DeletePersona -> deletePersona(event.persona)
        }
    }

    private fun deletePersona(persona: Persona) {
        viewModelScope.launch {
            try {
                deletePersonaUseCase.invoke(persona)
                _uiState.update { it.copy(mensaje = ConstantesUI.PERSONA_BORRADA) }
            } catch (e: Exception) {
                _uiState.update { it.copy(mensaje = ConstantesUI.ERROR_BORRAR_PERSONA) }
            }
        }
    }

    private fun cargarPersonas() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(lista = getPersonasUseCase.invoke()) }
            } catch (e: Exception) {
                _uiState.update { it.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS) }
            }
        }
    }
}