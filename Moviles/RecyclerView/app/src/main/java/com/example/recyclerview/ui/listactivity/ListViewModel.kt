package com.example.recyclerview.ui.listactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerview.domain.usecases.DeletePersonaUseCase
import com.example.recyclerview.domain.usecases.GetPersonasUseCase
import com.example.recyclerview.ui.common.Constantes

class ListViewModel(
    private val getPersonasUseCase: GetPersonasUseCase,
    private val deletePersonaUseCase: DeletePersonaUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(ListState(null, emptyList()))
    val uiState: LiveData<ListState> get() = _uiState

    fun deletePersona(email: String) {
        if (!deletePersonaUseCase(email)) {
            _uiState.value = ListState(
                lista = getPersonasUseCase(),
                error = Constantes.ERROR_BORRAR_PERSONA,
            )
        } else {
            _uiState.value = ListState(
                lista = getPersonasUseCase(),
                error = Constantes.PERSONA_BORRADA,
            )
        }
    }

    fun cargarPersonas() {
        _uiState.value = ListState(
            lista = getPersonasUseCase(),
            error = Constantes.NADA,
        )
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
                @Suppress(Constantes.UNCHECKED_CAST)
                return ListViewModel(
                    getPersonasUseCase,
                    deletePersonaUseCase,
                ) as T
            }
            throw IllegalArgumentException(Constantes.UNKNOWN_VIEW_MODEL_CLASS)
        }
    }
}