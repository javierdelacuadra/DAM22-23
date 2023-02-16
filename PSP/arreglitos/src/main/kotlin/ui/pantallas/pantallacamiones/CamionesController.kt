package ui.pantallas.pantallacamiones

import io.github.palexdev.materialfx.controls.MFXDatePicker
import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import servicios.modelo.Camion
import java.net.URL
import java.util.*

class CamionesController : Initializable {

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
        idColumn = TableColumn<Camion, Int>("ID")
        modeloColumn = TableColumn<Camion, String>("Modelo")
        fechaColumn = TableColumn<Camion, String>("Fecha")
        camionesTable = TableView<Camion>()
        camionesTable.columns.addAll(idColumn, modeloColumn, fechaColumn)
        camionesTable.items = FXCollections.observableArrayList(viewModel.getAllCamiones())
    }

    private fun agregarCamion() {
        val camion = Camion(0, modeloTextField.text, fechaDatePicker.value.toString())
        viewModel.agregarCamion(camion)
    }

    private fun actualizarCamion() {
        val camionActual = camionesTable.selectionModel.selectedItem
        val camion = Camion(camionActual.id, modeloTextField.text, fechaDatePicker.value.toString())
        viewModel.actualizarCamion(camion)
    }

    private fun borrarCamion() {
        val camionActual = camionesTable.selectionModel.selectedItem
        viewModel.eliminarCamion(camionActual.id.toString())
    }
}
