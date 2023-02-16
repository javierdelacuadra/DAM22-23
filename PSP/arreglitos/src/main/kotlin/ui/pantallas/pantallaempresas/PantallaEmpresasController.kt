package ui.pantallas.pantallaempresas

import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import servicios.modelo.Empresa
import java.net.URL
import java.util.*

class PantallaEmpresasController : Initializable {

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
        idColumn = TableColumn<Empresa, Int>("ID")
        nombreColumn = TableColumn<Empresa, String>("Nombre")
        direccionColumn = TableColumn<Empresa, String>("Direccion")
        tablaEmpresas = TableView<Empresa>()
        tablaEmpresas.columns.addAll(idColumn, nombreColumn, direccionColumn)
        tablaEmpresas.items = viewModel.getAllEmpresas()
    }

    private fun agregarEmpresa() {
        val empresa = Empresa(0, textoNombre.text, textoDireccion.text, emptyList())
        viewModel.agregarEmpresa(empresa)
    }

    private fun actualizarEmpresa() {
        val empresaActual = tablaEmpresas.selectionModel.selectedItem
        val empresa = Empresa(empresaActual.id, textoNombre.text, textoDireccion.text, empresaActual.camiones)
        viewModel.actualizarEmpresa(empresa)
    }

    private fun borrarEmpresa() {
        val empresaActual = tablaEmpresas.selectionModel.selectedItem
        viewModel.eliminarEmpresa(empresaActual.id.toString())
    }
}