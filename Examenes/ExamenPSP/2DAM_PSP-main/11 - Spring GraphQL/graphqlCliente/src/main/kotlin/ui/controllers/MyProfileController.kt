package ui.controllers

import dao.PersonaDao
import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.fxml.FXML
import javafx.scene.control.Label
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import ui.common.BaseScreenController
import utils.Trither

class MyProfileController: BaseScreenController() {

    @FXML
    lateinit var lblName: Label

    @FXML
    lateinit var txtName: MFXTextField

    @FXML
    lateinit var txtSurname: MFXTextField

    private val dao: PersonaDao = PersonaDao

    override fun principalCargado() {
        val logged = getPrincipalController()!!.userLoggedIn
        lblName.text = logged?.name
        txtName.text = logged?.name
        txtSurname.text = logged?.surname
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun editPersona(){
        if (txtName.text.isNullOrEmpty() || txtSurname.text.isNullOrEmpty()) {
            getPrincipalController()!!.showAlertError("Los campos no pueden estar vacios")
        } else {
            GlobalScope.launch(Dispatchers.JavaFx) {
                dao.updatePersonaLogged(txtName.text, txtSurname.text).collect { trither ->
                    if (trither is Trither.Success) {
                        getPrincipalController()!!.showLoading(false)
                        lblName.text = trither.data?.name
                        getPrincipalController()!!.showAlertInformation("Datos actualizados correctamente")
                        getPrincipalController()!!.userLoggedIn = trither.data

                    }
                    if (trither is Trither.Error) {
                        getPrincipalController()!!.showLoading(false)
                        trither.message?.let { error ->
                            getPrincipalController()!!.showAlertError(error)
                        }
                    }
                    if (trither is Trither.Loading) {
                        getPrincipalController()!!.showLoading(true)
                    }
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deletePersona(){
        GlobalScope.launch(Dispatchers.JavaFx) {
            dao.deletePersonaLogged().collect { trither ->
                when (trither) {
                    is Trither.Success -> {
                        getPrincipalController()!!.showLoading(false)
                        getPrincipalController()!!.showAlertInformation("Te voy a extrañar, ${trither.data?.name?: "amigo"}")
                        getPrincipalController()!!.backToLogin()

                    }

                    is Trither.Loading -> {
                        getPrincipalController()!!.showLoading(true)
                    }

                    else -> {
                        trither.message?.let { error ->
                            getPrincipalController()!!.showLoading(false)
                            getPrincipalController()!!.showAlertError(error)
                        }
                    }
                }
            }
        }
    }
}