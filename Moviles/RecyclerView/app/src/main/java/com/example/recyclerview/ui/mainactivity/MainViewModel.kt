package com.example.recyclerview.ui.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerview.R
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.domain.usecases.AddPersonaUseCase
import com.example.recyclerview.domain.usecases.DeletePersonaUseCase
import com.example.recyclerview.domain.usecases.GetPersonasUseCase
import com.example.recyclerview.ui.common.Constantes
import com.example.recyclerview.utils.StringProvider

class MainViewModel(
    private val stringProvider: StringProvider,
    private val addPersonaUseCase: AddPersonaUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    fun addPersona(persona: Persona): Boolean {
        if (persona.nombre.isEmpty() || persona.email.isEmpty() || persona.password.isEmpty()) {
            _uiState.value =
                MainState(mensaje = stringProvider.getString(R.string.error_campos_vacios))
            return false
        } else if (persona.email.count { it == Constantes.ARROBA } > Constantes.UNO) {
            _uiState.value =
                MainState(mensaje = stringProvider.getString(R.string.error_formato_email))
            return false
        } else if (!addPersonaUseCase(persona)) {
            _uiState.value = MainState(
                mensaje = stringProvider.getString(R.string.error_al_guardar_persona)
            )
            return false
        }
        _uiState.value = _uiState.value?.copy(
            persona = persona,
            mensaje = stringProvider.getString(R.string.persona_guardada),
        )
        return true
    }

    /**
     * Factory class to instantiate the [ViewModel] instance.
     */
    class MainViewModelFactory(
        private val stringProvider: StringProvider,
        private val addPersonaUseCase: AddPersonaUseCase,
        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress(Constantes.UNCHECKED_CAST)
                return MainViewModel(
                    stringProvider,
                    addPersonaUseCase,
                ) as T
            }
            throw IllegalArgumentException(Constantes.UNKNOWN_VIEW_MODEL_CLASS)
        }
    }
}