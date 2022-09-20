package ui.pantallas.pantallamain;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;
import modelo.Cliente;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.Pantallas;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Log4j2
public class PantallaMainController extends BasePantallaController implements Initializable {


    @Inject
    public PantallaMainController(Instance<Object> instance) {
        this.instance = instance;
    }


    @FXML
    private Menu menuOptions;

    @FXML
    private Menu newspapersMenu;

    @FXML
    private Menu articlesMenu;

    @FXML
    private MenuItem productosAdmin;

    @FXML
    private MenuItem clientesAdmin;

    @FXML
    private MenuItem logoutAdmin;

    @FXML
    private MenuItem salirAdmin;

    @FXML
    private MenuItem menuConfig;

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
        alert.setTitle("Salir de la aplicación");
        alert.setContentText("¿Está seguro que desea salir de la aplicación?");
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
        newspapersMenu.setVisible(false);
        articlesMenu.setVisible(false);
        menuOptions.setVisible(false);
        cargarPantalla(Pantallas.PANTALLAMAIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newspapersMenu.setVisible(false);
        articlesMenu.setVisible(false);
        menuOptions.setVisible(false);
        cargarPantalla(Pantallas.PANTALLALOGIN);
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
            log.error(e.getMessage(), e);
        }
    }

    public void sacarAlertError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private Cliente usuarioActual;

    public void onLoginHecho(Cliente c) {
        usuarioActual = c;
        if (usuarioActual.getNombre().equalsIgnoreCase("admin")) {
            newspapersMenu.setVisible(true);
            articlesMenu.setVisible(true);
            menuOptions.setVisible(true);
            cargarPantalla(Pantallas.PANTALLAADMINPRODUCTOS);
        } else {
            newspapersMenu.setVisible(true);
            articlesMenu.setVisible(true);
            menuOptions.setVisible(true);
            cargarPantalla(Pantallas.PANTALLACLIENTE);
        }
    }

    public Cliente getUsuarioActual() {
        return usuarioActual;
    }

    @FXML
    public void configuracion() {
        cargarPantalla(Pantallas.PANTALLACONFIG);
    }

    @FXML
    public void menuAdminProductos(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.PANTALLAADMINPRODUCTOS);
    }

    @FXML
    public void menuAdminClientes(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.PANTALLAADMINCLIENTES);
    }

    public void compras(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.PANTALLACLIENTE);
    }

    public void cambiarNombre(String nombre) {
        usuarioActual.setNombre(nombre);
    }

    public void listNewspapersMenu(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.LISTNEWSPAPERSCREEN);
    }

    public void addNewspaperMenu(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.ADDNEWSPAPERSCREEN);
    }

    public void updateNewspaperMenu(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.UPDATENEWSPAPERSCREEN);
    }

    public void deleteNewspaperMenu(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.DELETENEWSPAPERSCREEN);
    }

    public void listArticlesMenu(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.LISTARTICLESCREEN);
    }

    public void addArticleMenu(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.ADDARTICLESCREEN);
    }

    public void updateArticleMenu(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.UPDATEARTICLESCREEN);
    }

    public void deleteArticleMenu(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.DELETEARTICLESCREEN);
    }
}