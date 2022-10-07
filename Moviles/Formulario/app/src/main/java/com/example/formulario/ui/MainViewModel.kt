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

    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    fun addPersona(persona: Persona) {
        if (!addPersonaUseCase(persona)) {
            _uiState.value = MainState(
                error = stringProvider.getString(R.string.name),
            )
        }
    }

    fun getPersonas(id: Int) {
        val personas = getPersonasUseCase()
        if (personas.size < id || id < 0) {
            _uiState.value = _uiState.value?.copy(error = "error")

        } else
            _uiState.value = _uiState.value?.copy(persona = personas[id])
    }

    fun deletePersona(persona: Persona) {
        deletePersonaUseCase(persona)
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
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                stringProvider,
                addPersonaUseCase,
                getPersonasUseCase,
                deletePersonaUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}