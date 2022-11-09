package com.example.recyclerview.ui.addactivity

import androidx.lifecycle.*
import com.example.recyclerview.R
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.AddPersonaUseCase
import com.example.recyclerview.ui.common.Constantes
import com.example.recyclerview.utils.StringProvider
import kotlinx.coroutines.launch

class AddViewModel(
    private val stringProvider: StringProvider,
    private val addPersonaUseCase: AddPersonaUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(AddState())
    val uiState: LiveData<AddState> get() = _uiState

    fun addPersona(persona: Persona): Boolean {
        if (persona.nombre.isEmpty() || persona.email.isEmpty() || persona.password.isEmpty()) {
            _uiState.value =
                AddState(mensaje = stringProvider.getString(R.string.error_campos_vacios))
            return false
        } else if (persona.email.count { it == Constantes.ARROBA } > Constantes.UNO) {
            _uiState.value =
                AddState(mensaje = stringProvider.getString(R.string.error_formato_email))
            return false
        } else {
            viewModelScope.launch {
                try {
                    addPersonaUseCase.invoke(persona)
                    _uiState.value =
                        AddState(mensaje = stringProvider.getString(R.string.persona_guardada))
                } catch (e: Exception) {
                    _uiState.value = AddState(mensaje = e.message)
                }
            }
            return true
        }
    }

    /**
     * Factory class to instantiate the [ViewModel] instance.
     */
    class AddViewModelFactory(
        private val stringProvider: StringProvider,
        private val addPersonaUseCase: AddPersonaUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
                @Suppress(Constantes.UNCHECKED_CAST)
                return AddViewModel(
                    stringProvider,
                    addPersonaUseCase,
                ) as T
            }
            throw IllegalArgumentException(Constantes.UNKNOWN_VIEW_MODEL_CLASS)
        }
    }
}