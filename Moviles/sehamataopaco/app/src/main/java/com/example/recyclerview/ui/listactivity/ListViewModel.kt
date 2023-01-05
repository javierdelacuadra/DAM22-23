package com.example.recyclerview.ui.listactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.personas.DeletePersonaUseCase
import com.example.recyclerview.domain.usecases.tarjetas.GetPersonasWithTarjetasUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val deletePersonaUseCase: DeletePersonaUseCase,
    private val getPersonasWithTarjetasUseCase: GetPersonasWithTarjetasUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(ListState(null, emptyList()))
    val uiState: LiveData<ListState> get() = _uiState

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
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.PERSONA_BORRADA)
                cargarPersonas()
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_BORRAR_PERSONA)
            }
        }
    }

    private fun cargarPersonas() {
        viewModelScope.launch {
            try {
                _uiState.value =
                    _uiState.value?.copy(lista = getPersonasWithTarjetasUseCase.invoke())
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }
}