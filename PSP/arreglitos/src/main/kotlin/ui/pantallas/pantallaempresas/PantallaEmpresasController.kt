package ui.pantallas.pantallaempresas

import javafx.fxml.Initializable
import javafx.scene.control.Alert
import java.net.URL
import java.util.*

class PantallaEmpresasController : Initializable {
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        Alert(Alert.AlertType.INFORMATION, "Pantalla empresas").showAndWait()
    }
}