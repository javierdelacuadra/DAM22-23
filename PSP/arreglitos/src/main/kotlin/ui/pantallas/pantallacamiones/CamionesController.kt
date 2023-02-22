package ui.pantallas.pantallacamiones

import io.github.palexdev.materialfx.controls.MFXDatePicker
import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
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

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        idColumn.cellValueFactory = PropertyValueFactory("id")
        modeloColumn.cellValueFactory = PropertyValueFactory("modelo")
        fechaColumn.cellValueFactory = PropertyValueFactory("fechaConstruccion")
        CoroutineScope(coroutineContext).launch { camionesTable.items = FXCollections.observableArrayList(viewModel.getAllCamiones()) }
        camionesTable.refresh()
    }

    suspend fun agregarCamion() {
        val camion = Camion(0, modeloTextField.text, fechaDatePicker.value.toString())
        viewModel.agregarCamion(camion)
    }

    suspend fun actualizarCamion() {
        val camionActual = camionesTable.selectionModel.selectedItem
        val camion = Camion(camionActual.id, modeloTextField.text, fechaDatePicker.value.toString())
        viewModel.actualizarCamion(camion)
    }

    suspend fun borrarCamion() {
        val camionActual = camionesTable.selectionModel.selectedItem
        viewModel.eliminarCamion(camionActual.id)
    }
}