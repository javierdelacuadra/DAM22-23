package ui.pantallas.pantallacamiones

import io.github.palexdev.materialfx.controls.MFXButton
import io.github.palexdev.materialfx.controls.MFXDatePicker
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
import ui.pantallas.common.BasePantallaController
import java.net.URL
import java.util.*

class CamionesController : Initializable, BasePantallaController() {

    private val viewModel = CamionesViewModel()

    @FXML
    private lateinit var idColumn: TableColumn<Camion, Int>

    @FXML
    private lateinit var modeloColumn: TableColumn<Camion, String>

    @FXML
    private lateinit var fechaColumn: TableColumn<Camion, String>

    @FXML
    private lateinit var camionesTable: TableView<Camion>

    @FXML
    private lateinit var modeloTextField: MFXTextField

    @FXML
    private lateinit var fechaDatePicker: MFXDatePicker

    @FXML
    private lateinit var agregarButton: MFXButton

    @FXML
    private lateinit var actualizarButton: MFXButton

    @FXML
    private lateinit var borrarButton: MFXButton

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        idColumn = TableColumn<Camion, Int>("ID")
        modeloColumn = TableColumn<Camion, String>("Modelo")
        fechaColumn = TableColumn<Camion, String>("Fecha")
        camionesTable = TableView<Camion>()
        camionesTable.columns.addAll(idColumn, modeloColumn, fechaColumn)
        CoroutineScope(coroutineContext).launch { camionesTable.items = FXCollections.observableArrayList(viewModel.getAllCamiones()) }
    }

    override fun principalCargado() {
        agregarButton.setOnAction { CoroutineScope(coroutineContext).launch { agregarCamion() } }
        actualizarButton.setOnAction { CoroutineScope(coroutineContext).launch { actualizarCamion() } }
        borrarButton.setOnAction { CoroutineScope(coroutineContext).launch { borrarCamion() } }
    }

    private suspend fun agregarCamion() {
        val camion = Camion(0, modeloTextField.text, fechaDatePicker.value.toString())
        viewModel.agregarCamion(camion)
    }

    private suspend fun actualizarCamion() {
        val camionActual = camionesTable.selectionModel.selectedItem
        val camion = Camion(camionActual.id, modeloTextField.text, fechaDatePicker.value.toString())
        viewModel.actualizarCamion(camion)
    }

    private suspend fun borrarCamion() {
        val camionActual = camionesTable.selectionModel.selectedItem
        viewModel.eliminarCamion(camionActual.id)
    }
}