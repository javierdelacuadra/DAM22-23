package com.example.recyclerview.ui.addtarjeta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.R
import com.example.recyclerview.domain.modelo.Tarjeta
import com.example.recyclerview.domain.usecases.tarjetas.AddTarjetaUseCase
import com.example.recyclerview.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTarjetaViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addTarjetaUseCase: AddTarjetaUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(AddTarjetaState())
    val uiState: LiveData<AddTarjetaState> get() = _uiState

    fun handleEvent(event: AddTarjetaEvent) {
        when (event) {
            is AddTarjetaEvent.AddTarjeta -> addTarjeta(
                event.numeroTarjeta,
                event.mes,
                event.year,
                event.cvv,
                event.email
            )
        }
    }

    private fun addTarjeta(
        numeroTarjeta: String,
        mes: String,
        year: String,
        cvv: String,
        email: String
    ) {
        if (validarCampos(numeroTarjeta, mes, year, cvv)) {
            val fecha = "$mes/$year"
            val tarjeta = Tarjeta(numeroTarjeta, fecha, cvv.toInt(), email)
            viewModelScope.launch {
                try {
                    addTarjetaUseCase.invoke(tarjeta)
                    _uiState.value =
                        AddTarjetaState(mensaje = stringProvider.getString(R.string.tarjeta_guardada))
                } catch (e: Exception) {
                    _uiState.value =
                        AddTarjetaState(mensaje = stringProvider.getString(R.string.error_al_guardar_tarjeta))
                }
            }
        }
    }

    private fun validarCampos(
        numeroTarjeta: String,
        mes: String,
        year: String,
        cvv: String
    ): Boolean {
        if (numeroTarjeta.isNotEmpty() && mes.isNotEmpty() && year.isNotEmpty() && cvv.isNotEmpty()) {
            if (numeroTarjeta.length == 16 && cvv.length == 3) {
                return true
            } else {
                _uiState.value =
                    AddTarjetaState(mensaje = stringProvider.getString(R.string.formato_tarjeta_incorrecto))
            }
        } else {
            _uiState.value =
                AddTarjetaState(mensaje = stringProvider.getString(R.string.error_campos_vacios))
        }
        return false
    }
}