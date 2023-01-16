package com.example.recyclerview.ui.loginactivity.fragments.logindoctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.login.CheckLoginDoctorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginDoctorViewModel @Inject constructor(
    private val checkLogin: CheckLoginDoctorUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(LoginDoctorState(null))
    val uiState: LiveData<LoginDoctorState> get() = _uiState

    fun handleEvent(event: LoginDoctorEvent) {
        when (event) {
            is LoginDoctorEvent.Login -> checkLogin(event.nombre, event.email)
        }
    }

    private fun checkLogin(nombre: String, email: String) {
        viewModelScope.launch {
            val loginSuccess = checkLogin.invoke(nombre, email)
            _uiState.value = _uiState.value?.copy(loginSuccess = loginSuccess)
        }
    }
}