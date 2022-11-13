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
import lombok.extern.log4j.Log4j2;
import model.Reader;
import ui.common.ConstantesUI;
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
    private Menu optionsMenu;

    @FXML
    private Menu newspapersMenu;

    @FXML
    private Menu articlesMenu;

    @FXML
    private Menu readersMenu;

    @FXML
    private Menu readArticlesMenu;

    @FXML
    private Menu subscriptionsMenu;

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
        optionsMenu.setVisible(false);
        readersMenu.setVisible(false);
        readArticlesMenu.setVisible(false);
        subscriptionsMenu.setVisible(false);
        cargarPantalla(Pantallas.PANTALLAMAIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newspapersMenu.setVisible(false);
        articlesMenu.setVisible(false);
        optionsMenu.setVisible(false);
        readersMenu.setVisible(false);
        readArticlesMenu.setVisible(false);
        subscriptionsMenu.setVisible(false);
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

    public void onLoginHecho(boolean admin) {
        if (admin) {
            optionsMenu.setVisible(true);
            newspapersMenu.setVisible(true);
            articlesMenu.setVisible(true);
            readersMenu.setVisible(true);
            cargarPantalla(Pantallas.LISTNEWSPAPERSCREEN);
        } else {
            optionsMenu.setVisible(true);
            readArticlesMenu.setVisible(true);
            subscriptionsMenu.setVisible(true);
            cargarPantalla(Pantallas.ADDREADARTICLESCREEN);
        }
    }

    public Reader reader;

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public void listNewspapersMenu() {
        cargarPantalla(Pantallas.LISTNEWSPAPERSCREEN);
    }

    public void deleteNewspaperMenu() {
        cargarPantalla(Pantallas.DELETENEWSPAPERSCREEN);
    }

    public void listArticlesMenu() {
        cargarPantalla(Pantallas.LISTARTICLESCREEN);
    }

    public void addArticleMenu() {
        cargarPantalla(Pantallas.ADDARTICLESCREEN);
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

    public void addReadArticlesMenu() {
        cargarPantalla(Pantallas.ADDREADARTICLESCREEN);
    }

    public void addSubscriptionMenu() {
        cargarPantalla(Pantallas.ADDSUBSCRIPTIONSCREEN);
    }

    public void addNewspaperMenu() {
        cargarPantalla(Pantallas.ADDNEWSPAPERSCREEN);
    }

    public void updateNewspaperMenu() {
        cargarPantalla(Pantallas.UPDATENEWSPAPERSCREEN);
    }

    public void queriesMenu() {
        cargarPantalla(Pantallas.QUERIESSCREEN);
    }

    public void createAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(error);
        alert.setContentText(error);
        alert.showAndWait();
    }
}