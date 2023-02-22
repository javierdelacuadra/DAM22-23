package ui.controllers

import dao.PersonaDao
import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.fxml.FXML
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import ui.common.BaseScreenController
import utils.Trither

class RegisterController: BaseScreenController(){
    @FXML lateinit var mfxSurname: MFXTextField

    @FXML lateinit var mfxName: MFXTextField

    @FXML
    private lateinit var mfxUsername: MFXTextField

    @FXML
    private lateinit var mfxPassword: MFXTextField

    private val dao = PersonaDao

    @OptIn(DelicateCoroutinesApi::class)
    fun addUser() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            dao.register(mfxName.text, mfxSurname.text, mfxUsername.text, mfxPassword.text).collect {
                when (it) {
                    is Trither.Success -> {
                        getPrincipalController()!!.showLoading(false)
                        getPrincipalController()!!.showAlertInformation("Usuario registrado con éxito")
                        backToLogin()
                    }

                    is Trither.Error -> {
                        getPrincipalController()!!.showLoading(false)
                        getPrincipalController()!!.showAlertError(it.message)

                    }

                    else -> {
                        getPrincipalController()!!.showLoading(true)
                    }
                }
            }
        }
    }

    fun backToLogin() {
        getPrincipalController()!!.backToLogin()
    }
}