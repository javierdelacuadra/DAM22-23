package com.example.recyclerview.ui.loginactivity.fragments.loginusuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.domain.usecases.login.CheckLoginUsuarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginUsuarioViewModel @Inject constructor(
    private val checkLogin: CheckLoginUsuarioUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(LoginUsuarioState(null))
    val uiState: LiveData<LoginUsuarioState> get() = _uiState

    fun handleEvent(event: LoginUsuarioEvent) {
        when (event) {
            is LoginUsuarioEvent.Login -> checkLogin(event.email, event.password)
        }
    }

    private fun checkLogin(email: String, password: String) {
        viewModelScope.launch {
            val loginSuccess = checkLogin.invoke(email, password)
            _uiState.value = _uiState.value?.copy(loginSuccess = loginSuccess)
        }
    }
}