package ui.screens.principal;


import common.CommonConstants;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;
import org.miutils.domain.modelo.Reader;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;
import ui.screens.common.Screens;

import java.io.IOException;
import java.util.Optional;

@Log4j2
public class PrincipalController extends BaseScreenController {

    @FXML
    private Menu menuNews;

    @FXML
    private MenuItem menuLogout;
    @FXML
    private MenuItem menuNewsUp;
    @FXML
    private Menu menuReader;
    // objeto especial para DI
    Instance<Object> instance;

    @FXML
    private MenuBar menuPrincipal;

    private Stage primaryStage;


    @FXML
    public BorderPane root;


    private final Alert alert;

    private Reader readerLoggedIn;

    private final PrincipalViewModel principalViewModel;

    @Inject
    public PrincipalController(Instance<Object> instance, PrincipalViewModel principalViewModel) {
        this.instance = instance;
        this.principalViewModel = principalViewModel;
        alert = new Alert(Alert.AlertType.NONE);
    }


    public void showAlertError(String mensaje) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.setResizable(true);
        alert.showAndWait();
    }

    public void showAlertInformation(String message) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }

    public boolean showAlertConfirmation(String mensaje) {
        Alert confAlert = new Alert(Alert.AlertType.CONFIRMATION);

        confAlert.getDialogPane().setContent(new Label(mensaje));
        confAlert.showAndWait();
        return confAlert.getResult().getButtonData().isDefaultButton();
    }

    private Pane cargarPantalla(String ruta) {
        Pane panePantalla = null;
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(ruta));
            BaseScreenController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.principalCargado();


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        cambioPantalla(panePantalla);
        return panePantalla;
    }


    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
    }


    public void initialize() {
        hideMenu();
        cargarPantalla(Screens.LOGIN.getRuta());
        principalViewModel.getState().addListener((observableValue, prState, prStateNew) ->
                Platform.runLater(() -> {
                    if (prStateNew.getError() != null) {
                        showAlertError(prStateNew.getError());
                    } else if (prStateNew.getSuccess() != null) {
                        showAlertInformation(prStateNew.getSuccess());
                    }
                }));
    }


    public void cargarPantalla(Screens pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }

    private void closeWindowEvent(WindowEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.getButtonTypes().remove(ButtonType.OK);
        alerta.getButtonTypes().add(ButtonType.CANCEL);
        alerta.getButtonTypes().add(ButtonType.YES);
        alerta.setTitle(ScreenConstants.QUIT_APPLICATION);
        alerta.setContentText(ScreenConstants.CLOSE_WITHOUT_SAVING);
        alerta.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alerta.showAndWait();


        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.CANCEL) {
                event.consume();
            }
        });


    }

    public void exit() {
        primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }


    public void menuClickNews(ActionEvent actionEvent) {
        if (ScreenConstants.MENU_NEWS_UP.equals(((MenuItem) actionEvent.getSource()).getId())) {
            cargarPantalla(Screens.UPDNEWS);
        } else {
            cargarPantalla(Screens.LISTNEWS);
        }
    }

    public void menuClickReader(ActionEvent actionEvent) {
        if (ScreenConstants.MENU_READER_UP.equals(((MenuItem) actionEvent.getSource()).getId())) {
            cargarPantalla(Screens.UPDREADER);
        } else {
            cargarPantalla(Screens.LISTREADERS);
        }
    }

    public void onLoginHecho(Reader reader) {
        readerLoggedIn = reader;

        menuPrincipal.setVisible(true);
        menuNews.setVisible(true);
        menuNewsUp.setVisible(false);
        menuReader.setVisible(true);
        menuLogout.setVisible(true);
        if (readerLoggedIn.getId() == CommonConstants.ID_ADMIN) {
            // si es admin, puede actualizar newspapers
            menuNewsUp.setVisible(true);
        } else if (readerLoggedIn.getId() == CommonConstants.ID_INVITADO) {
            // si es invitado, se desactivan opciones del menu
            menuNews.setVisible(false);
            menuReader.setVisible(false);
        }
        cargarPantalla(Screens.LISTNEWS);
    }

    public void backToLogin() {
        if (readerLoggedIn != null) {
            readerLoggedIn = null;
            hideMenu();
            principalViewModel.logout();
        }
        cargarPantalla(Screens.LOGIN);
    }

    public void register() {
        //por si viene desde el menu
        if (readerLoggedIn != null) {
            principalViewModel.logout();
            readerLoggedIn = null;
        }
        hideMenu();
        cargarPantalla(Screens.REGISTER);
    }

    public Reader getReaderLoggedIn() {
        return readerLoggedIn;
    }

    private void hideMenu() {
        menuPrincipal.setVisible(true);
        menuReader.setVisible(false);
        menuLogout.setVisible(false);
        menuNews.setVisible(false);
    }
}

