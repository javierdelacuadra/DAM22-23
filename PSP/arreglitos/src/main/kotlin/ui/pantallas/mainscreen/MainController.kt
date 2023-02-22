package ui.pantallas.mainscreen

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Menu
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.stage.WindowEvent
import ui.pantallas.common.BasePantallaController
import ui.pantallas.common.Pantallas
import java.io.IOException
import java.net.URL
import java.util.*

class MainController : Initializable, BasePantallaController() {

    @FXML
    lateinit var optionsMenu: Menu

    private var primaryStage: Stage? = null

    @FXML
    var root: BorderPane? = null

    private var alert: Alert = Alert(Alert.AlertType.NONE)

    private fun showAlertError(mensaje: String?) {
        alert.alertType = Alert.AlertType.ERROR
        alert.contentText = mensaje
        alert.isResizable = true
        alert.showAndWait()
    }

    private fun cargarPantalla(pantalla: Pantallas): Pane? {
        var panePantalla: Pane? = null
        try {
            val fxmlLoader = FXMLLoader()
            panePantalla = fxmlLoader.load(javaClass.getResourceAsStream(pantalla.ruta))
            val pantallaController: BasePantallaController = fxmlLoader.getController()
            pantallaController.setPrincipalController(this)
            pantallaController.principalCargado()
        } catch (e: IOException) {
            showAlertError("Error al cargar la pantalla: ${pantalla.ruta} ($e)")
        }
        root!!.center = panePantalla
        return panePantalla
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
            } else {
                primaryStage!!.close()
                exit()
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

    fun listCamionesMenu() {
        cargarPantalla(Pantallas.PANTALLA_CAMIONES)
    }

    fun listConductoresMenu() {
        cargarPantalla(Pantallas.PANTALLA_CONDUCTORES)
    }

    fun listEmpresasMenu() {
        cargarPantalla(Pantallas.PANTALLA_EMPRESAS)
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        cargarPantalla(Pantallas.PANTALLA_CAMIONES)
    }
}