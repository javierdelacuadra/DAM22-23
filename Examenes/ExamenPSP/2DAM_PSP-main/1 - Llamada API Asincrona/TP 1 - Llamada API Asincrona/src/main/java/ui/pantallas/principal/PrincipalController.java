package ui.pantallas.principal;


import io.github.palexdev.materialfx.controls.MFXButton;
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

import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;
import ui.pantallas.common.Pantallas;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrincipalController extends BasePantallaController {

    @FXML
    public MFXButton btnComenzar;


    // objeto especial para DI
    Instance<Object> instance;

    @FXML
    private MenuBar menuPrincipal;
    private Stage primaryStage;

    @FXML
    public BorderPane root;


    private final Alert alert;

    @Inject
    public PrincipalController(Instance<Object> instance) {
        this.instance = instance;
        alert = new Alert(Alert.AlertType.NONE);
    }



    public void sacarAlertError(String mensaje) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.setResizable(true);
        alert.showAndWait();
    }

    private Pane cargarPantalla(String ruta) {
        Pane panePantalla = null;
        ResourceBundle r = ResourceBundle.getBundle(ConstantesPantallas.I_18_N_TEXTOS_UI);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            fxmlLoader.setResources(r);
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(ruta));
            BasePantallaController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.principalCargado();
        } catch (IOException e) {
            sacarAlertError(e.getMessage());
        }
        cambioPantalla(panePantalla);
        return panePantalla;
    }

    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
    }


    public void initialize() {
        menuPrincipal.setVisible(false);
        setPrincipalController(this);
    }

    public void activateMenu() {
        menuPrincipal.setVisible(true);
    }

    public void cargarPantalla(Pantallas pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }

    private void closeWindowEvent(WindowEvent event) {
        Alert alertClose = new Alert(Alert.AlertType.INFORMATION);
        alertClose.getButtonTypes().remove(ButtonType.OK);
        alertClose.getButtonTypes().add(ButtonType.CANCEL);
        alertClose.getButtonTypes().add(ButtonType.YES);
        alertClose.setTitle(ConstantesPantallas.SALIR_DE_LA_APLICACION);
        alertClose.setContentText(ConstantesPantallas.CLOSE_WITHOUT_SAVING);
        alertClose.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alertClose.showAndWait();
        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.CANCEL) {
                event.consume();
            }
        });
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    @FXML
    private void menuClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case ConstantesPantallas.MENU_ITEM_LIST_CITY -> cargarPantalla(Pantallas.LISTADO_CIUDADES);
            case ConstantesPantallas.MENU_ITEM_DETALLE_PAIS -> cargarPantalla(Pantallas.DETALLE);
            default -> cargarPantalla(Pantallas.PRINCIPAL);
        }
    }


    public void comenzar() {
        cargarPantalla(Pantallas.LISTADO_CIUDADES);
    }

    public void exit() {
        primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
