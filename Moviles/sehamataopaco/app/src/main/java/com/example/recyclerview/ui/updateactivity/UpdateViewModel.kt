package com.example.recyclerview.ui.updateactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.personas.UpdatePersonaUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
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
}