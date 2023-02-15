package ui.pantallas.mainscreen

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import ui.pantallas.common.Pantallas
import ui.pantallas.pantallacamiones.CamionesController
import java.io.IOException
import java.net.URL
import java.util.*

class MainController : Initializable {

    @FXML
    lateinit var root: BorderPane
    @FXML
    lateinit var optionsMenu: Menu
    @FXML
    lateinit var salirMenuItem: MenuItem
    @FXML
    lateinit var camionesMenu: Menu
    @FXML
    lateinit var conductoresMenu: Menu
    @FXML
    lateinit var empresasMenu: Menu

    private lateinit var primaryStage: Stage

    fun setPrimaryStage(primaryStage: Stage) {
        this.primaryStage = primaryStage
    }

    fun getPrimaryStage(): Stage {
        return primaryStage
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        salirMenuItem.setOnAction { salir() }
        camionesMenu.setOnAction { listCamionesMenu() }
        conductoresMenu.setOnAction { listConductoresMenu() }
        empresasMenu.setOnAction { listEmpresasMenu() }
        cargarPantalla(Pantallas.PANTALLA_CAMIONES)
    }

    private fun cargarPantalla(pantalla: Pantallas) {
        try {
            val loader = FXMLLoader(javaClass.getResource(pantalla.ruta))
            val controller = CamionesController()
            loader.setController(controller)
            val root = loader.load<Parent>()
            val scene = Scene(root)
            if (!::primaryStage.isInitialized) {
                primaryStage = Stage()
                primaryStage.title = "Cliente GraphQL"
                primaryStage.scene = scene
                primaryStage.show()
                primaryStage.isResizable = false
                primaryStage.setOnCloseRequest { event ->
                    event.consume()
                    salir()
                }
            } else {
                primaryStage.scene = scene
                primaryStage.show()
            }
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