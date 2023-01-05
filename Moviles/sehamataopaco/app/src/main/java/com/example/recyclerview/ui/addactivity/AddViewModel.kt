package com.example.recyclerview.ui.addactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.R
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.personas.AddPersonaUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import com.example.recyclerview.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addPersonaUseCase: AddPersonaUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(AddState())
    val uiState: LiveData<AddState> get() = _uiState

    fun handleEvent(event: AddEvent) {
        when (event) {
            is AddEvent.AddPersona -> addPersona(event.persona)
        }
    }

    private fun addPersona(persona: Persona): Boolean {
        if (persona.nombre.isEmpty() || persona.email.isEmpty() || persona.password.isEmpty()) {
            _uiState.value =
                AddState(mensaje = stringProvider.getString(R.string.error_campos_vacios))
            return false
        } else if (persona.email.count { it == ConstantesUI.ARROBA } > ConstantesUI.UNO) {
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
                    _uiState.value =
                        AddState(mensaje = stringProvider.getString(R.string.error_al_guardar_persona))
                }
            }
            return true
        }
    }

}