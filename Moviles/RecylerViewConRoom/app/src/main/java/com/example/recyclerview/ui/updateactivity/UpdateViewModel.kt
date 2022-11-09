package com.example.recyclerview.ui.updateactivity

import androidx.lifecycle.*
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.UpdatePersonaUseCase
import com.example.recyclerview.ui.common.Constantes
import kotlinx.coroutines.launch

class UpdateViewModel(
    private val updatePersonaUseCase: UpdatePersonaUseCase,
) : ViewModel() {
    private val _state = MutableLiveData<UpdateState>()
    val state: LiveData<UpdateState> = _state

    fun updatePersona(persona: Persona) {
        viewModelScope.launch {
            try {
                updatePersonaUseCase.invoke(persona)
                _state.value = UpdateState(mensaje = Constantes.PERSONA_ACTUALIZADA)
            } catch (e: Exception) {
                _state.value = UpdateState(mensaje = e.message)
            }
        }
    }

    class UpdateViewModelFactory(
        private val updatePersonaUseCase: UpdatePersonaUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UpdateViewModel::class.java)) {
                @Suppress(Constantes.UNCHECKED_CAST)
                return UpdateViewModel(
                    updatePersonaUseCase,
                ) as T
            }
            throw IllegalArgumentException(Constantes.UNKNOWN_VIEW_MODEL_CLASS)
        }
    }
}