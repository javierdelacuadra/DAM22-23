package com.example.recyclerview.ui.splashactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.login.GetActualUserUseCase
import com.example.recyclerview.ui.common.ConstantesUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkActualUser: GetActualUserUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData(SplashState(null))
    val uiState: LiveData<SplashState> get() = _uiState

    fun handleEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.CheckActualUser -> getActualUser()
        }
    }

    private fun getActualUser() {
        viewModelScope.launch {
            try {
                val actualUser = checkActualUser.invoke()
                if (actualUser.rol == ConstantesUI.USUARIO) {
                    _uiState.value = _uiState.value?.copy(rol = actualUser.rol)
                } else if (actualUser.rol == ConstantesUI.DOCTOR) {
                    _uiState.value = _uiState.value?.copy(rol = actualUser.rol)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(rol = ConstantesUI.ROL_NADA)
            }
        }
    }

}