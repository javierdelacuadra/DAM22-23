package com.example.recyclerview.ui.listequipos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.equipos.DeleteEquipoUseCase
import com.example.recyclerview.domain.usecases.equipos.GetEquiposWithComponentesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEquiposViewModel @Inject constructor(
    private val getEquiposWithComponentesUseCase: GetEquiposWithComponentesUseCase,
    private val deleteEquipoUseCase: DeleteEquipoUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(ListEquiposState(null, emptyList()))
    val uiState: LiveData<ListEquiposState> get() = _uiState

    fun handleEvent(event: ListEquiposEvent) {
        when (event) {
            is ListEquiposEvent.GetEquipos -> loadEquipos()
            is ListEquiposEvent.DeleteEquipos -> deleteEquipo(event.nombreEquipo)
        }
    }

    private fun deleteEquipo(nombreEquipo: String) {
        viewModelScope.launch {
            try {
                deleteEquipoUseCase.invoke(nombreEquipo)
                _uiState.value = _uiState.value?.copy(mensaje = "Equipo borrado con exito")
                loadEquipos()
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value?.copy(mensaje = "No se puede borrar porque tiene componentes")
            }
        }
    }

    private fun loadEquipos() {
        viewModelScope.launch {
            try {
                _uiState.value =
                    _uiState.value?.copy(
                        lista = getEquiposWithComponentesUseCase.invoke()
                            .sortedBy { equipo -> equipo.puesto })
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = "error al cargar los equipos")
            }
        }
    }
}