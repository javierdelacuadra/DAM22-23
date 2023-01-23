package ui.pantallas.pantallamain;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.Usuario;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.Pantallas;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PantallaMainController extends BasePantallaController implements Initializable {

    private final PantallaMainViewModel viewModel;

    @Inject
    public PantallaMainController(PantallaMainViewModel viewModel, Instance<Object> instance) {
        this.viewModel = viewModel;
        this.instance = instance;
    }

    @FXML
    private Menu optionsMenu;

    @FXML
    private Menu newspapersMenu;

    @FXML
    private Menu readersMenu;

    @FXML
    private Menu carpetasMenu;

    @FXML
    private BorderPane root;

    @FXML
    private Stage primaryStage;

    Instance<Object> instance;

    private void closeWindowEvent(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle(ConstantesUI.EXIT_THE_APPLICATION);
        alert.setContentText(ConstantesUI.ARE_YOU_SURE_YOU_WANT_TO_EXIT_THE_APPLICATION);
        alert.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.CANCEL) {
                event.consume();
            } else {
                Platform.exit();
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
        newspapersMenu.setVisible(false);
        optionsMenu.setVisible(false);
        readersMenu.setVisible(false);
        carpetasMenu.setVisible(false);
        viewModel.logout();
        cargarPantalla(Pantallas.PANTALLAMAIN);
    }

    public void onLoginHecho(boolean admin) {
        if (admin) {
            optionsMenu.setVisible(true);
            newspapersMenu.setVisible(true);
            readersMenu.setVisible(true);
            carpetasMenu.setVisible(true);
            cargarPantalla(Pantallas.LISTNEWSPAPERSCREEN);
        } else {
            optionsMenu.setVisible(true);
            cargarPantalla(Pantallas.LISTNEWSPAPERSCREEN);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarPantalla(Pantallas.LOGINSCREEN);
        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.mensaje != null) {
                Platform.runLater(() -> createAlert(newState.mensaje));
            }
        });
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

    public Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void listNewspapersMenu() {
        cargarPantalla(Pantallas.LISTNEWSPAPERSCREEN);
    }

    public void deleteNewspaperMenu() {
        cargarPantalla(Pantallas.DELETENEWSPAPERSCREEN);
    }

    public void listReadersMenu() {
        cargarPantalla(Pantallas.LISTREADERSCREEN);
    }

    public void deleteReadersMenu() {
        cargarPantalla(Pantallas.DELETEREADERSCREEN);
    }

    public void addReaderMenu() {
        cargarPantalla(Pantallas.ADDREADERSCREEN);
    }

    public void updateReaderMenu() {
        cargarPantalla(Pantallas.UPDATEREADERSCREEN);
    }

    public void addNewspaperMenu() {
        cargarPantalla(Pantallas.ADDNEWSPAPERSCREEN);
    }

    public void updateNewspaperMenu() {
        cargarPantalla(Pantallas.UPDATENEWSPAPERSCREEN);
    }

    public void createAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(error);
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void listCarpetasMenu() {
        cargarPantalla(Pantallas.LISTCARPETASSCREEN);
    }

    public void addCarpetaMenu() {
        cargarPantalla(Pantallas.ADDCARPETASSCREEN);
    }
}