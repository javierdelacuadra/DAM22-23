package ui.controllers

import credentials.CachedCredentials
import dao.PersonaDao
import io.github.palexdev.materialfx.controls.MFXPasswordField
import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import ui.common.BaseScreenController
import utils.Trither

class LoginController: BaseScreenController() {
    @FXML
    lateinit var mfxUser: MFXTextField

    @FXML
    lateinit var mfxPassword: MFXPasswordField

    private val dao = PersonaDao

    private val ca = CachedCredentials
    override fun principalCargado() {
        addListenerState()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun tryLogin() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            dao.tryLogin(mfxUser.text, mfxPassword.text).collect { trither ->
                when (trither) {
                    is Trither.Success -> {
                        getPrincipalController()!!.showLoading(false)
                        ca.username = mfxUser.text
                        ca.password = mfxPassword.text
                        ca.role = trither.data?.login?.role
                        trither.data?.let { persona ->
                            getPrincipalController()!!.onLoginHecho(persona)
                        }
                    }

                    is Trither.Error -> {
                        getPrincipalController()!!.showLoading(false)
                        trither.message?.let { error ->
                            getPrincipalController()!!.showAlertError(error)
                        }
                    }

                    is Trither.Loading -> {
                        getPrincipalController()!!.showLoading(true)
                    }
                }
            }
        }

    }

    private fun addListenerState() {
        mfxUser.onAction = EventHandler { _: ActionEvent? -> tryLogin() }
        mfxPassword.onAction = EventHandler { _: ActionEvent? -> tryLogin() }
    }

    fun goToSignup() {
        getPrincipalController()!!.goToSignUp()
    }
}