package ui.pantallas.pantallaempresas

import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import servicios.modelo.Empresa
import ui.pantallas.common.BasePantallaController
import java.net.URL
import java.util.*

class PantallaEmpresasController : Initializable, BasePantallaController() {

    private val viewModel = PantallaEmpresasViewModel()

    @FXML
    private lateinit var tablaEmpresas: TableView<Empresa>

    @FXML
    private lateinit var idColumn: TableColumn<Empresa, Int>

    @FXML
    private lateinit var nombreColumn: TableColumn<Empresa, String>

    @FXML
    private lateinit var direccionColumn: TableColumn<Empresa, String>

    @FXML
    private lateinit var textoNombre: MFXTextField

    @FXML
    private lateinit var textoDireccion: MFXTextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        idColumn.cellValueFactory = PropertyValueFactory("id")
        nombreColumn.cellValueFactory = PropertyValueFactory("nombre")
        direccionColumn.cellValueFactory = PropertyValueFactory("direccion")
        CoroutineScope(coroutineContext).launch { tablaEmpresas.items = viewModel.getAllEmpresas() }
    }

    suspend fun agregarEmpresa() {
        val empresa = Empresa(0, textoNombre.text, textoDireccion.text, emptyList())
        viewModel.agregarEmpresa(empresa)
    }

    suspend fun actualizarEmpresa() {
        val empresaActual = tablaEmpresas.selectionModel.selectedItem
        val empresa = Empresa(empresaActual.id, textoNombre.text, textoDireccion.text, empresaActual.camiones)
        viewModel.actualizarEmpresa(empresa)
    }

    suspend fun borrarEmpresa() {
        val empresaActual = tablaEmpresas.selectionModel.selectedItem
        viewModel.eliminarEmpresa(empresaActual.id)
    }
}