package com.example.formulario.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.formulario.R
import com.example.formulario.domain.modelo.Persona
import com.example.formulario.domain.usecases.AddPersonaUseCase
import com.example.formulario.domain.usecases.DeletePersonaUseCase
import com.example.formulario.domain.usecases.GetPersonasUseCase
import com.example.formulario.utils.StringProvider

class MainViewModel(
    private val stringProvider: StringProvider,
    private val addPersonaUseCase: AddPersonaUseCase,
    private val getPersonasUseCase: GetPersonasUseCase,
    private val deletePersonaUseCase: DeletePersonaUseCase,
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

    fun getPersonaAnterior(id: Int) {
        if (id <= Constantes.UNO) {
            _uiState.value = _uiState.value?.copy(
                mensaje = stringProvider.getString(R.string.error_no_hay_mas_personas)
            )
        } else {
            _uiState.value = _uiState.value?.copy(
                persona = getPersonasUseCase()[id - Constantes.DOS],
                mensaje = null,
            )
        }
    }

    fun getPersonaSiguiente(id: Int) {
        if (id + Constantes.UNO > GetPersonasUseCase().invoke().size) {
            _uiState.value = _uiState.value?.copy(
                mensaje = stringProvider.getString(R.string.error_no_hay_mas_personas)
            )
        } else {
            _uiState.value = _uiState.value?.copy(
                persona = getPersonasUseCase()[id],
                mensaje = null,
            )
        }
    }

    fun deletePersona(persona: Persona): Boolean {
        if (persona.id < Constantes.CERO || persona.id - Constantes.UNO >= GetPersonasUseCase().invoke().size) {
            _uiState.value = _uiState.value?.copy(
                mensaje = stringProvider.getString(R.string.error_persona_no_existe)
            )
            return false
        } else if (!deletePersonaUseCase(persona)) {
            _uiState.value = MainState(
                mensaje = stringProvider.getString(R.string.error_al_eliminar_persona),
            )
            return false
        }
        _uiState.value = _uiState.value?.copy(
            persona = Persona(
                Constantes.CERO,
                Constantes.NADA,
                Constantes.NADA,
                Constantes.NADA,
                false
            ),
            mensaje = stringProvider.getString(R.string.persona_eliminada),
        )
        return true
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addPersonaUseCase: AddPersonaUseCase,
    private val getPersonasUseCase: GetPersonasUseCase,
    private val deletePersonaUseCase: DeletePersonaUseCase,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress(Constantes.UNCHECKED_CAST)
            return MainViewModel(
                stringProvider,
                addPersonaUseCase,
                getPersonasUseCase,
                deletePersonaUseCase,
            ) as T
        }
        throw IllegalArgumentException(Constantes.UNKNOWN_VIEW_MODEL_CLASS)
    }
}