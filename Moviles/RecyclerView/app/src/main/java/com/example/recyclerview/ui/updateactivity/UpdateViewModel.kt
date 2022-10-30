package com.example.recyclerview.ui.updateactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.UpdatePersonaUseCase
import com.example.recyclerview.ui.common.Constantes

class UpdateViewModel(
    private val updatePersonaUseCase: UpdatePersonaUseCase,
) : ViewModel() {
    private val _state = MutableLiveData<UpdateState>()
    val state: LiveData<UpdateState> = _state

    fun updatePersona(persona: Persona) {
        val result = updatePersonaUseCase(persona)
        if (result) {
            _state.value = UpdateState(persona, Constantes.PERSONA_ACTUALIZADA)
        } else {
            _state.value = UpdateState(persona, Constantes.PERSONA_NO_ACTUALIZADA)
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