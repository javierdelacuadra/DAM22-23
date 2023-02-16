package ui.pantallas.pantallacamiones

import io.github.palexdev.materialfx.controls.MFXButton
import io.github.palexdev.materialfx.controls.MFXDatePicker
import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import servicios.modelo.Camion
import java.net.URL
import java.util.*

class CamionesController : Initializable {

//    private val viewModel = CamionesViewModel()

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
    private lateinit var addButton: MFXButton

    @FXML
    private lateinit var updateButton: MFXButton

    @FXML
    private lateinit var deleteButton: MFXButton

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        idColumn = TableColumn<Camion, Int>("ID")
        modeloColumn = TableColumn<Camion, String>("Modelo")
        fechaColumn = TableColumn<Camion, String>("Fecha")

    }

    private fun agregarCamion() {
        // Implementar lógica para agregar un camión
    }

    private fun actualizarCamion() {
        // Implementar lógica para actualizar un camión
    }

    private fun borrarCamion() {
        // Implementar lógica para borrar un camión
    }
}
