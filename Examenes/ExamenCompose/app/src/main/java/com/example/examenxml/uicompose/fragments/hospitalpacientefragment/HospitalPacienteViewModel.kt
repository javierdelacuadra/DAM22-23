package com.example.examenxml.uicompose.fragments.hospitalpacientefragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenxml.data.repositories.HospitalesRepository
import com.example.examenxml.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HospitalPacienteViewModel @Inject constructor(
    private val hospitalesRepository: HospitalesRepository,
) : ViewModel() {

    private val _uiHospitalPacienteState: MutableStateFlow<HospitalPacienteEvent.HospitalPacienteState> by lazy {
        MutableStateFlow(HospitalPacienteEvent.HospitalPacienteState())
    }

    val uiHospitalPacienteState: StateFlow<HospitalPacienteEvent.HospitalPacienteState> =
        _uiHospitalPacienteState

    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun handleEvent(event: HospitalPacienteEvent.Eventos) {
        when (event) {
            is HospitalPacienteEvent.Eventos.LoadHospitales -> {
                cargarHospitales()
            }
            is HospitalPacienteEvent.Eventos.LoadPacientes -> {
                cargarPacientes(event.id)
            }
        }
    }

    private fun cargarHospitales() {
        viewModelScope.launch {
            _uiHospitalPacienteState.update { it.copy(cargando = true) }
            hospitalesRepository.fetchHospitales()
                .catch { cause -> _uiError.send(cause.message ?: "") }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiHospitalPacienteState.update {
                                it.copy(
                                    error = result.message,
                                    cargando = false
                                )
                            }
                        }
                        is NetworkResult.Success -> {
                            _uiHospitalPacienteState.update {
                                it.copy(
                                    hospitales = result.data!!,
                                    cargando = false
                                )
                            }
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun cargarPacientes(id: UUID) {
        val hospitales = _uiHospitalPacienteState.value.hospitales
        val hospital = hospitales.find { it.id == id }
        hospital?.let {
            _uiHospitalPacienteState.update { it.copy(pacientes = it.pacientes) }
        }
    }
}