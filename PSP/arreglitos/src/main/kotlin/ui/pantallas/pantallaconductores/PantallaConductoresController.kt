package ui.pantallas.pantallaconductores

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
    private lateinit var textoNombre: MFXTextField

    @FXML
    private lateinit var textoTelefono: MFXTextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        idColumn.cellValueFactory = PropertyValueFactory("id")
        nombreColumn.cellValueFactory = PropertyValueFactory("nombre")
        telefonoColumn.cellValueFactory = PropertyValueFactory("telefono")
        CoroutineScope(coroutineContext).launch { tablaConductores.items = FXCollections.observableArrayList(viewModel.getAllConductores())}
    }

    suspend fun agregarConductor() {
        val conductor = Conductor(0, textoNombre.text, textoTelefono.text, Camion(0, "", ""))
        viewModel.agregarConductor(conductor)
    }

    suspend fun actualizarConductor() {
        val conductorActual = tablaConductores.selectionModel.selectedItem
        val conductor = Conductor(conductorActual.id, textoNombre.text, textoTelefono.text, Camion(0, "", ""))
        viewModel.actualizarConductor(conductor)
    }

    suspend fun borrarConductor() {
        val conductorActual = tablaConductores.selectionModel.selectedItem
        viewModel.eliminarConductor(conductorActual.id)
    }

}