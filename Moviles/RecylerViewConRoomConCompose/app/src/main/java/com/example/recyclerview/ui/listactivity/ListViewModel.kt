package com.example.recyclerview.ui.listactivity

import androidx.lifecycle.*
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.DeletePersonaUseCase
import com.example.recyclerview.domain.usecases.GetPersonasUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import kotlinx.coroutines.launch

class ListViewModel(
    private val getPersonasUseCase: GetPersonasUseCase,
    private val deletePersonaUseCase: DeletePersonaUseCase,
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_BORRAR_PERSONA)
            }
        }
    }

    private fun cargarPersonas() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value?.copy(lista = getPersonasUseCase.invoke())
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }

    /**
     * Factory class to instantiate the [ViewModel] instance.
     */
    class ListViewModelFactory(
        private val getPersonasUseCase: GetPersonasUseCase,
        private val deletePersonaUseCase: DeletePersonaUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
                @Suppress(ConstantesUI.UNCHECKED_CAST)
                return ListViewModel(
                    getPersonasUseCase,
                    deletePersonaUseCase,
                ) as T
            }
            throw IllegalArgumentException(ConstantesUI.UNKNOWN_VIEW_MODEL_CLASS)
        }
    }
}