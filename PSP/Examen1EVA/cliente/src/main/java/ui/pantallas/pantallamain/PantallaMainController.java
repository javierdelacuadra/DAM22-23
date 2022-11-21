package ui.pantallas.pantallamain;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.common.BasePantallaController;
import ui.common.Pantallas;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PantallaMainController extends BasePantallaController implements Initializable {

    Instance<Object> instance;

    @Inject
    public PantallaMainController(Instance<Object> instance) {
        this.instance = instance;
    }

    @FXML
    public BorderPane root;

    @FXML
    private Stage primaryStage;

    private void closeWindowEvent(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Salir de la aplicación");
        alert.setContentText("Seguro que quieres salir?");
        alert.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.CANCEL) {
                event.consume();
            }
        });
    }

    public void salir() {
        Platform.exit();
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    public void logout() {
        cargarPantalla(Pantallas.PANTALLAMAIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarPantalla(Pantallas.PANTALLAEQUIPOS);
    }

    private void cargarPantalla(Pantallas pantalla) {
        Pane panePantalla;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(pantalla.getRuta()));
            BasePantallaController pantallaController = fxmlLoader.getController();
            pantallaController.setPantallaMainController(this);
            pantallaController.principalCargado();
            root.setCenter(panePantalla);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(error);
        alert.setContentText(error);
        alert.showAndWait();
    }
}
