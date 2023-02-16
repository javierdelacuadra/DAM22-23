package ui.pantallas.mainscreen

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.Menu
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import ui.pantallas.common.BasePantallaController
import ui.pantallas.common.Pantallas
import ui.pantallas.pantallacamiones.CamionesController
import ui.pantallas.pantallaconductores.PantallaConductoresController
import ui.pantallas.pantallaempresas.PantallaEmpresasController
import java.io.IOException
import java.net.URL
import java.util.*

class MainController : Initializable, BasePantallaController() {

    @FXML
    lateinit var root: BorderPane
    @FXML
    lateinit var optionsMenu: Menu

    private lateinit var primaryStage: Stage

    fun setPrimaryStage(primaryStage: Stage) {
        this.primaryStage = primaryStage
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        cargarPantalla(Pantallas.PANTALLA_CAMIONES)
    }

    private fun cargarPantalla(pantalla: Pantallas) {
        try {
            val loader = FXMLLoader(javaClass.getResource(pantalla.ruta))
            val controller = when (pantalla) {
                Pantallas.PANTALLA_CAMIONES -> CamionesController()
                Pantallas.PANTALLA_CONDUCTORES -> PantallaConductoresController()
                Pantallas.PANTALLA_EMPRESAS -> PantallaEmpresasController()
                else -> throw IllegalArgumentException("No se ha encontrado el controlador para la pantalla $pantalla")
            }
            loader.setController(controller)
            val anchorpane = loader.load<Parent>()
            root.center = anchorpane
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun salir() {
        Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que quieres salir?").showAndWait().ifPresent { response ->
            if (response.text == "OK") {
                primaryStage.close()
            }
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
}