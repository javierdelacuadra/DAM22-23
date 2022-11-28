package com.example.recyclerview.ui.addequipo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.modelo.Equipo
import com.example.recyclerview.domain.usecases.equipos.AddEquipoUseCase
import com.example.recyclerview.domain.usecases.equipos.GetEquiposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEquipoViewModel @Inject constructor(
    private val addEquipoUseCase: AddEquipoUseCase,
    private val getEquiposUseCase: GetEquiposUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(AddEquipoState())
    val uiState: LiveData<AddEquipoState> get() = _uiState

    fun handleEvent(event: AddEquipoEvent) {
        when (event) {
            is AddEquipoEvent.AddEquipo -> addEquipo(event.equipo)
        }
    }

    private fun addEquipo(equipo: Equipo) {
        if (validarCampos(equipo)) {
            viewModelScope.launch {
                try {
                    addEquipoUseCase.invoke(equipo)
                    _uiState.value =
                        AddEquipoState(mensaje = "Equipo añadido con éxito")
                } catch (e: Exception) {
                    _uiState.value =
                        AddEquipoState(mensaje = "Error al añadir el equipo")
                }
            }
        } else {
            _uiState.value =
                AddEquipoState(mensaje = "Ya existe un equipo con ese puesto")
        }
    }

    private fun validarCampos(equipoActual: Equipo) : Boolean {
        val puesto = equipoActual.puesto
        var puestoUnico = true
        viewModelScope.launch {
            getEquiposUseCase.invoke().stream().forEach {
                if (it.puesto == puesto) {
                    puestoUnico = false
                }
            }
        }
        return puestoUnico
    }
}