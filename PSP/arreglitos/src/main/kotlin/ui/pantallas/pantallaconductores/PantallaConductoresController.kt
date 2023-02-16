package ui.pantallas.pantallaconductores

import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import servicios.modelo.Conductor
import java.net.URL
import java.util.*

class PantallaConductoresController : Initializable {

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
    private lateinit var IDCamion: MFXTextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        idColumn = TableColumn<Conductor, Int>("ID")
        nombreColumn = TableColumn<Conductor, String>("Nombre")
        telefonoColumn = TableColumn<Conductor, String>("Telefono")
        idCamionColumn = TableColumn<Conductor, Int>("ID Camion")
        tablaConductores = TableView<Conductor>()
        tablaConductores.columns.addAll(idColumn, nombreColumn, telefonoColumn, idCamionColumn)
        tablaConductores.items = FXCollections.observableArrayList(viewModel.getAllConductores())
    }

}