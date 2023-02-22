package ui.screens.principal;


import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;

import model.Reader;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;
import ui.screens.common.Screens;
//import ui.pantallas.login.LoginController;

import java.io.IOException;
import java.util.Optional;

@Log4j2
public class PrincipalController extends BaseScreenController {
    @FXML
    private Menu menuNewspapers;
    @FXML
    private Menu menuArticles;
    @FXML
    private Menu menuReader;
    @FXML
    private MenuItem menuSubsGet;
    @FXML
    private MenuItem menuSubsAdd;
    @FXML
    private MenuItem menuSubsUp;
    @FXML
    private MenuItem menuSubsDel;
    @FXML
    private MenuItem menuRAGet;
    @FXML
    private MenuItem menuRAAdd;
    @FXML
    private MenuItem menuRAUp;
    @FXML
    private MenuItem menuRADel;


    // objeto especial para DI
    Instance<Object> instance;

    @FXML
    private MenuBar menuPrincipal;

    private Stage primaryStage;


    private Reader actualUser;

    //    private Cromo cromoSeleccionado;
//
    public Reader getActualUser() {
        return actualUser;
    }
//    public Cromo getActualCromo(){return cromoSeleccionado;}

    @FXML
    private BorderPane root;


    private Alert alert;

    @FXML
    private MFXTextField mfxUser;

    @FXML
    private MFXPasswordField mfxPassword;


    @Inject
    public PrincipalController(Instance<Object> instance) {
        this.instance = instance;
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


    public void logout() {
        actualUser = null;
        menuPrincipal.setVisible(false);
        cargarPantalla(Screens.LOGIN);
    }

    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
    }


    public void initialize() {
//        cargarPantalla(Screens.ADDREADART);
        menuPrincipal.setVisible(false);
        cargarPantalla(Screens.LOGIN);
        //menuPrincipal.setVisible(false);

    }


    public void cargarPantalla(Screens pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }

    private void closeWindowEvent(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle(ScreenConstants.QUIT_APPLICATION);
        alert.setContentText(ScreenConstants.CLOSE_WITHOUT_SAVING);
        alert.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();


        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.CANCEL) {
                event.consume();
            }
        });
    }

    public void exit(ActionEvent actionEvent) {
        primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }


    public double getHeight() {
        return root.getScene().getWindow().getHeight();
    }

    public double getWidth() {
//        return 600;
        return root.getScene().getWindow().getWidth();
    }


    public void menuClickNews(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case ScreenConstants.MENU_NEWS_LIST:
                cargarPantalla(Screens.LISTNEWS);
                break;
            case ScreenConstants.MENU_NEWS_ADD:
                cargarPantalla(Screens.ADDNEWS);
                break;
            case ScreenConstants.MENU_NEWS_UP:
                cargarPantalla(Screens.UPDNEWS);
                break;
            case ScreenConstants.MENU_NEWS_DEL:
                cargarPantalla(Screens.DELNEWS);
                break;
            default:
                break;
        }
    }

    public void menuClickArt(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case ScreenConstants.MENU_ART_LIST:
                cargarPantalla(Screens.LISTART);
                break;
            case ScreenConstants.MENU_ART_ADD:
                cargarPantalla(Screens.ADDART);
                break;
            case ScreenConstants.MENU_ART_UP:
                break;
            case ScreenConstants.MENU_ART_DEL:
                break;
            default:
                break;
        }
    }

    public void menuClickReader(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case ScreenConstants.MENU_READER_LIST -> cargarPantalla(Screens.LISTREADERS);
            case ScreenConstants.MENU_READER_ADD -> cargarPantalla(Screens.ADDREADER);
            case ScreenConstants.MENU_READER_UP -> cargarPantalla(Screens.UPDREADER);
            case ScreenConstants.MENU_READER_DEL -> cargarPantalla(Screens.DELREADERS);
        }
    }

    public void menuClickRA(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case ScreenConstants.MENU_RA_LIST:
                break;
            case ScreenConstants.MENU_RA_ADD:
                cargarPantalla(Screens.ADDREADART);
                break;
            case ScreenConstants.MENU_RA_UP:
                break;
            case ScreenConstants.MENU_RA_DEL:
                break;
            default:
                break;
        }
    }

    public void menuClickSubs(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case ScreenConstants.MENU_SUBS_LIST:
                break;
            case ScreenConstants.MENU_SUBS_ADD:
                break;
            case ScreenConstants.MENU_SUBS_UP:
                break;
            case ScreenConstants.MENU_SUBS_DEL:
                break;
            default:
                break;
        }
    }


    //evento de otra pantalla
    public void onLoginHecho(Reader usuario) {
        actualUser = usuario;
        menuPrincipal.setVisible(true);
        if (actualUser.getId() > 0) {
            // NORMAL READER
            menuNewspapers.setVisible(false);
            menuArticles.setVisible(false);
            menuReader.setVisible(false);

            menuRAAdd.setVisible(true);
            menuRAGet.setVisible(false);
            menuRAUp.setVisible(false);
            menuRADel.setVisible(false);

            menuSubsGet.setVisible(false);
            menuSubsUp.setVisible(false);
            menuSubsDel.setVisible(false);

            cargarPantalla(Screens.ADDREADART);
        } else {
            // ADMIN
            menuRAAdd.setVisible(false);
            cargarPantalla(Screens.LISTNEWS);
        }
    }

}

