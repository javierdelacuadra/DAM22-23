package com.example.recyclerview.ui.addtarjeta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.R
import com.example.recyclerview.domain.modelo.Tarjeta
import com.example.recyclerview.domain.usecases.AddTarjetaUseCase
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
            is AddTarjetaEvent.AddTarjeta -> addTarjeta(event.tarjeta)
        }
    }

    private fun addTarjeta(tarjeta: Tarjeta): Boolean {
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
            return true
    }
}