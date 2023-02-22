package ui.controllers

import credentials.CachedCredentials
import domain.modelo.Persona
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.stage.WindowEvent
import lombok.extern.log4j.Log4j2
import ui.common.BaseScreenController
import ui.common.Screens
import java.io.IOException
import kotlin.system.exitProcess

@Log4j2
class PrincipalController {


    @FXML lateinit var menuAdmin: Menu
    @FXML lateinit var menuUser: Menu
    @FXML lateinit var menuLogout: MenuItem

    @FXML
    lateinit var menuPrincipal: MenuBar

    private var primaryStage: Stage? = null


    @FXML
    var root: BorderPane? = null


    private var alert: Alert = Alert(Alert.AlertType.NONE)

    var userLoggedIn: Persona? = null


    private var ca: CachedCredentials = CachedCredentials









    fun showAlertError(mensaje: String?) {
        alert.alertType = Alert.AlertType.ERROR
        alert.contentText = mensaje
        alert.isResizable = true
        alert.showAndWait()
    }

    fun showAlertInformation(message: String?) {
        val infoAlert = Alert(Alert.AlertType.INFORMATION)
        infoAlert.contentText = message
        infoAlert.showAndWait()
    }

    private fun cargarPantalla(ruta: String): Pane? {
        var panePantalla: Pane? = null
        try {
            val fxmlLoader = FXMLLoader()
            panePantalla = fxmlLoader.load(javaClass.getResourceAsStream(ruta))
            val pantallaController: BaseScreenController = fxmlLoader.getController()
            pantallaController.setPrincipalController(this)
            pantallaController.principalCargado()
        } catch (e: IOException) {
            showAlertError("Error al cargar la pantalla: $ruta ($e)")
        }
        cambioPantalla(panePantalla)
        return panePantalla
    }


    private fun cambioPantalla(pantallaNueva: Pane?) {
        root!!.center = pantallaNueva
    }


    fun initialize() {
        goToLogin()
    }


    private fun cargarPantalla(pantalla: Screens) {
        cambioPantalla(cargarPantalla(pantalla.ruta))
    }

    private fun closeWindowEvent(event: WindowEvent) {
        val alerta = Alert(Alert.AlertType.INFORMATION)
        alerta.buttonTypes.remove(ButtonType.OK)
        alerta.buttonTypes.add(ButtonType.CANCEL)
        alerta.buttonTypes.add(ButtonType.YES)
        alerta.title = "Quit application?"
        alerta.contentText = "Close without saving?"
        alerta.initOwner(primaryStage!!.owner)
        val res = alerta.showAndWait()
        res.ifPresent { buttonType: ButtonType ->
            if (buttonType == ButtonType.CANCEL) {
                event.consume()
            } else{
                //Parar ejecución sin inyección de dependencias
                primaryStage!!.close()
                exitProcess(0)
            }
        }
    }

    fun exit() {
        primaryStage?.fireEvent(WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST))
    }

    fun setStage(stage: Stage?) {
        primaryStage = stage
        primaryStage!!.addEventFilter(
            WindowEvent.WINDOW_CLOSE_REQUEST
        ) { event: WindowEvent ->
            closeWindowEvent(
                event
            )
        }
    }


    fun onLoginHecho(user: Persona) {
        userLoggedIn = user
        menuPrincipal.isVisible = true
        menuLogout.isVisible = true
        menuUser.isVisible = true
        menuAdmin.isVisible = ca.role.equals("ROLE_ADMIN")
        cargarPantalla(Screens.LIST_MASCOTAS)
    }

    fun backToLogin() {
        goToLogin()
    }

    private fun goToLogin() {
        logout()
        menuPrincipal.isVisible = true
        cargarPantalla(Screens.LOGIN)
    }

    private fun logout() {
        userLoggedIn = null
        ca.invalidate()
        menuUser.isVisible = false
        menuAdmin.isVisible = false
        menuLogout.isVisible = false
    }

    fun goToSignUp() {
        logout()
        cargarPantalla(Screens.REGISTER)
    }

    fun showLoading(show: Boolean = true) {
        //Set cursor to wait
        if (show) {
            root!!.cursor = javafx.scene.Cursor.WAIT
        } else {
            root!!.cursor = javafx.scene.Cursor.DEFAULT
        }
    }

    fun goToMyProfile() {
        cargarPantalla(Screens.MY_PROFILE)
    }

    fun goToMyMascotas() {
        cargarPantalla(Screens.LIST_MASCOTAS)
    }

    fun goToListPersons() {
        cargarPantalla(Screens.LIST_PERSONAS)
    }

}