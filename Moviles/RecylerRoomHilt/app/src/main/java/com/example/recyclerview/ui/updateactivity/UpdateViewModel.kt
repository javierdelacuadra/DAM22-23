package com.example.recyclerview.ui.updateactivity

import androidx.lifecycle.*
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.UpdatePersonaUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class UpdateViewModel(
    private val updatePersonaUseCase: UpdatePersonaUseCase,
) : ViewModel() {
    private val _state = MutableLiveData<UpdateState>()
    val state: LiveData<UpdateState> = _state

    fun handleEvent(event: UpdateEvent) {
        when (event) {
            is UpdateEvent.UpdatePersona -> updatePersona(event.persona)
        }
    }

    private fun updatePersona(persona: Persona) {
        viewModelScope.launch {
            try {
                updatePersonaUseCase.invoke(persona)
                _state.value = UpdateState(mensaje = ConstantesUI.PERSONA_ACTUALIZADA)
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
                @Suppress(ConstantesUI.UNCHECKED_CAST)
                return UpdateViewModel(
                    updatePersonaUseCase,
                ) as T
            }
            throw IllegalArgumentException(ConstantesUI.UNKNOWN_VIEW_MODEL_CLASS)
        }
    }
}