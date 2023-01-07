package com.example.recyclerview.ui.usuarioactivity.fragments.pedircita

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.modelo.Cita
import com.example.recyclerview.domain.usecases.citas.PedirCitaUseCase
import com.example.recyclerview.domain.usecases.doctores.*
import com.example.recyclerview.domain.usecases.login.GetActualUserUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PedirCitaViewModel @Inject constructor(
    private val getEspecialidades: GetEspecialidadesUseCase,
    private val getDoctores: GetDoctoresUseCase,
    private val getFechas: GetFechasUseCase,
    private val getHoras: GetHoursUseCase,
    private val pedirCita: PedirCitaUseCase,
    private val getUsuarioActual: GetActualUserUseCase,
    private val getDoctorByName: GetDoctorByNameUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(PedirCitaState(null))
    val uiState: LiveData<PedirCitaState> get() = _uiState

    fun handleEvent(event: PedirCitaEvent) {
        when (event) {
            is PedirCitaEvent.GetEspecialidades -> cargarEspecialidades()
            is PedirCitaEvent.GetDoctores -> cargarDoctores(event.especialidad)
            is PedirCitaEvent.GetFechas -> cargarFechas(event.nombreDoctor)
            is PedirCitaEvent.GetHours -> cargarHoras(event.fecha, event.nombreDoctor)
            is PedirCitaEvent.PedirCita -> pedirCita(event.cita)
            is PedirCitaEvent.ClearStateButEspecialidad -> clearStateButEspecialidad()
            is PedirCitaEvent.ClearStateButEspecialidadAndDoctor -> clearStateButEspecialidadAndDoctor()
            is PedirCitaEvent.ClearStateButEspecialidadAndDoctorAndFecha -> clearStateButEspecialidadAndDoctorAndFecha()
        }
    }

    private fun cargarEspecialidades() {
        viewModelScope.launch {
            try {
                val listaEspecialidades = getEspecialidades.invoke()
                _uiState.value =
                    _uiState.value?.copy(listaEspecialidades = listaEspecialidades)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }

    private fun cargarDoctores(nombreEspecialidad: String) {
        viewModelScope.launch {
            val listaDoctores =
                getDoctores.invoke().filter { it.especialidad == nombreEspecialidad }
                    .map { it.nombre }
            _uiState.value = _uiState.value?.copy(listaDoctores = listaDoctores)
        }
    }

    private fun cargarFechas(nombreDoctor: String) {
        viewModelScope.launch {
            try {
                val listaFechas = getFechas.invoke(nombreDoctor)
                _uiState.value = _uiState.value?.copy(listaFechas = listaFechas)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }

    private fun cargarHoras(fecha: String, nombreDoctor: String) {
        viewModelScope.launch {
            try {
                _uiState.value =
                    _uiState.value?.copy(listaHoras = getHoras.invoke(fecha, nombreDoctor))
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }

    private fun pedirCita(cita: Cita) {
        viewModelScope.launch {
            try {
                val usuarioActual = getUsuarioActual.invoke()
                cita.emailUsuario = usuarioActual.email
                val doctor = getDoctorByName.invoke(cita.emailDoctor)
                cita.emailDoctor = doctor.email
                pedirCita.invoke(cita)
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.CITA_PEDIDA)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(mensaje = ConstantesUI.ERROR_CARGAR_PERSONAS)
            }
        }
    }

    private fun clearStateButEspecialidad() {

        _uiState.value = _uiState.value?.copy(listaDoctores = null)
        _uiState.value = _uiState.value?.copy(listaFechas = null)
        _uiState.value = _uiState.value?.copy(listaHoras = null)
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    private fun clearStateButEspecialidadAndDoctor() {
        _uiState.value = _uiState.value?.copy(listaFechas = null)
        _uiState.value = _uiState.value?.copy(listaHoras = null)
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    private fun clearStateButEspecialidadAndDoctorAndFecha() {
        _uiState.value = _uiState.value?.copy(listaHoras = null)
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }
}