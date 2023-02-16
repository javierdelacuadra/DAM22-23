package ui.pantallas.pantallaconductores

import javafx.fxml.Initializable
import javafx.scene.control.Alert
import java.net.URL
import java.util.*

class PantallaConductoresController : Initializable {
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        Alert(Alert.AlertType.INFORMATION, "Pantalla conductores").showAndWait()
    }

}