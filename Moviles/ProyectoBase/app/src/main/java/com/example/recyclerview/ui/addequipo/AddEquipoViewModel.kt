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
        if (validarPuesto(equipo)) {
            viewModelScope.launch {
                try {
                    addEquipoUseCase.invoke(equipo)
                    _uiState.value =
                        AddEquipoState(mensaje = "Equipo añadido con éxito")
                } catch (e: Exception) {
                    val mensaje = e.message
                    if (mensaje.equals("UNIQUE constraint failed: equipos.puesto (code 2067 SQLITE_CONSTRAINT_UNIQUE)")) {
                        _uiState.value =
                            AddEquipoState(mensaje = "Ya hay un equipo con ese puesto")
                    } else if (mensaje.equals("UNIQUE constraint failed: equipos.nombre (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)")) {
                        _uiState.value =
                            AddEquipoState(mensaje = "Ya hay un equipo con ese nombre")
                    } else {
                        _uiState.value =
                            AddEquipoState(mensaje = "hacker chino???")
                    }
                }
            }
        } else {
            _uiState.value =
                AddEquipoState(mensaje = "Ya existe un equipo con ese puesto")
        }
    }

    private fun validarPuesto(equipoActual: Equipo): Boolean {
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