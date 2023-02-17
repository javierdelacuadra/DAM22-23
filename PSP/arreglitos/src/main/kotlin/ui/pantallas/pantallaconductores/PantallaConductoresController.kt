package ui.pantallas.pantallaconductores

import io.github.palexdev.materialfx.controls.MFXButton
import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import servicios.modelo.Camion
import servicios.modelo.Conductor
import ui.pantallas.common.BasePantallaController
import java.net.URL
import java.util.*

class PantallaConductoresController : Initializable, BasePantallaController() {

    private val viewModel = PantallaConductoresViewModel()

    @FXML
    private lateinit var tablaConductores: TableView<Conductor>

    @FXML
    private lateinit var idColumn: TableColumn<Conductor, Int>

    @FXML
    private lateinit var nombreColumn: TableColumn<Conductor, String>

    @FXML
    private lateinit var telefonoColumn: TableColumn<Conductor, String>

    @FXML
    private lateinit var idCamionColumn: TableColumn<Conductor, Int>

    @FXML
    private lateinit var textoNombre: MFXTextField

    @FXML
    private lateinit var textoTelefono: MFXTextField

    @FXML
    private lateinit var agregarButton: MFXButton

    @FXML
    private lateinit var actualizarButton: MFXButton

    @FXML
    private lateinit var borrarButton: MFXButton

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        idColumn = TableColumn<Conductor, Int>("ID")
        nombreColumn = TableColumn<Conductor, String>("Nombre")
        telefonoColumn = TableColumn<Conductor, String>("Telefono")
        idCamionColumn = TableColumn<Conductor, Int>("ID Camion")
        tablaConductores = TableView<Conductor>()
        tablaConductores.columns.addAll(idColumn, nombreColumn, telefonoColumn, idCamionColumn)
        CoroutineScope(coroutineContext).launch { tablaConductores.items = FXCollections.observableArrayList(viewModel.getAllConductores())}
    }

    override fun principalCargado() {
        agregarButton.setOnAction { CoroutineScope(coroutineContext).launch { agregarConductor() } }
        actualizarButton.setOnAction { CoroutineScope(coroutineContext).launch { actualizarConductor() } }
        borrarButton.setOnAction { CoroutineScope(coroutineContext).launch { borrarConductor() } }
    }

    private suspend fun agregarConductor() {
        val conductor = Conductor(0, textoNombre.text, textoTelefono.text, Camion(0, "", ""))
        viewModel.agregarConductor(conductor)
    }

    private suspend fun actualizarConductor() {
        val conductorActual = tablaConductores.selectionModel.selectedItem
        val conductor = Conductor(conductorActual.id, textoNombre.text, textoTelefono.text, Camion(0, "", ""))
        viewModel.actualizarConductor(conductor)
    }

    private suspend fun borrarConductor() {
        val conductorActual = tablaConductores.selectionModel.selectedItem
        viewModel.eliminarConductor(conductorActual.id)
    }

}