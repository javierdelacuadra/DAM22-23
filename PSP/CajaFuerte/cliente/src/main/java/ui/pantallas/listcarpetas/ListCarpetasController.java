package ui.pantallas.listcarpetas;

import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Carpeta;
import modelo.Mensaje;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.constantes.ConstantesPantallas;

import java.util.Objects;

public class ListCarpetasController extends BasePantallaController {

    private final ListCarpetasViewModel viewModel;

    @Inject
    public ListCarpetasController(ListCarpetasViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Carpeta> tablaCarpetas;

    @FXML
    private TableColumn<Carpeta, String> columnaNombre;

    @FXML
    private TableColumn<Carpeta, String> columnaModo;

    @FXML
    private TableView<Mensaje> tablaMensajes;

    @FXML
    private TableColumn<Mensaje, String> columnaID;

    @FXML
    private TableColumn<Mensaje, String> columnaContenido;

    @FXML
    private MFXTextField textoMensaje;

    public void initialize() {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NOMBRE_CARPETA));
        columnaModo.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.MODO_EDICION));
        columnaID.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.ID));
        columnaContenido.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.CONTENIDO));
        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error != null) {
                Platform.runLater(() -> {
                    if (Objects.equals(newState.error, ConstantesPantallas.LA_PASSWORD_ES_INCORRECTA)) {
                        tablaMensajes.getItems().clear();
                    }
                    getPrincipalController().createAlert(newState.error);
                });
            }
            if (newState.carpetas != null) {
                Platform.runLater(() -> {
                    tablaCarpetas.getItems().clear();
                    tablaCarpetas.setItems(FXCollections.observableArrayList(newState.carpetas));
                });
            }
            if (newState.mensajes != null) {
                Platform.runLater(() -> {
                    tablaMensajes.getItems().clear();
                    tablaMensajes.setItems(FXCollections.observableArrayList(newState.mensajes));
                });
            }
        });
    }

    @Override
    public void principalCargado() {
        viewModel.getCarpetas(this.getPrincipalController().getUsuario().getId());
    }

    public void cargarMensajes() {
        Carpeta carpetaSeleccionada = tablaCarpetas.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(ConstantesPantallas.CARGAR_MENSAJES);
        dialog.setHeaderText(ConstantesPantallas.CARGAR_MENSAJES);
        dialog.setContentText(ConstantesPantallas.INTRODUCE_LA_PASSWORD_DE_LA_CARPETA + carpetaSeleccionada.getNombreCarpeta());
        dialog.showAndWait().ifPresent(passCarpeta -> {
            try {
                carpetaSeleccionada.setPassword(passCarpeta);
                viewModel.cargarMensajes(carpetaSeleccionada);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ConstantesPantallas.ERROR);
                alert.setHeaderText(ConstantesPantallas.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }

    public void addMensaje() {
        Carpeta carpetaSeleccionada = tablaCarpetas.getSelectionModel().getSelectedItem();
        Mensaje mensaje = new Mensaje();
        if (carpetaSeleccionada != null) {
            mensaje.setIDCarpeta(carpetaSeleccionada.getId());
            mensaje.setContenido(textoMensaje.getText());
            viewModel.addMensaje(mensaje, carpetaSeleccionada.getPassword());
            textoMensaje.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ConstantesPantallas.ERROR);
            alert.setHeaderText(ConstantesPantallas.ERROR);
            alert.setContentText(ConstantesPantallas.NO_HAY_NINGUNA_CARPETA_SELECCIONADA);
            alert.showAndWait();
        }
    }

    public void updateMensaje() {
        Carpeta carpetaSeleccionada = tablaCarpetas.getSelectionModel().getSelectedItem();
        Mensaje mensaje = tablaMensajes.getSelectionModel().getSelectedItem();
        if (carpetaSeleccionada != null && mensaje != null) {
            mensaje.setIDCarpeta(carpetaSeleccionada.getId());
            mensaje.setContenido(textoMensaje.getText());
            viewModel.updateMensaje(mensaje, carpetaSeleccionada.getPassword());
            textoMensaje.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ConstantesPantallas.ERROR);
            alert.setHeaderText(ConstantesPantallas.ERROR);
            alert.setContentText(ConstantesPantallas.NO_HAY_NINGUNA_CARPETA_SELECCIONADA);
            alert.showAndWait();
        }
    }

    public void deleteMensaje() {
        Carpeta carpetaSeleccionada = tablaCarpetas.getSelectionModel().getSelectedItem();
        Mensaje mensaje = tablaMensajes.getSelectionModel().getSelectedItem();
        if (carpetaSeleccionada != null && mensaje != null) {
            mensaje.setIDCarpeta(carpetaSeleccionada.getId());
            mensaje.setContenido(textoMensaje.getText());
            viewModel.deleteMensaje(mensaje);
            textoMensaje.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ConstantesPantallas.ERROR);
            alert.setHeaderText(ConstantesPantallas.ERROR);
            alert.setContentText(ConstantesPantallas.NO_HAY_NINGUNA_CARPETA_SELECCIONADA);
            alert.showAndWait();
        }
    }

    public void rellenarTextoMensaje() {
        Mensaje mensajeSeleccionado = tablaMensajes.getSelectionModel().getSelectedItem();
        if (mensajeSeleccionado != null) {
            textoMensaje.setText(mensajeSeleccionado.getContenido());
        }
    }

    public void cambiarPasswordCarpeta() {
        Carpeta carpetaSeleccionada = tablaCarpetas.getSelectionModel().getSelectedItem();
        if (carpetaSeleccionada != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(ConstantesPantallas.CAMBIAR_PASSWORD_DE_CARPETA);
            dialog.setHeaderText(ConstantesPantallas.CAMBIAR_PASSWORD_DE_CARPETA);
            dialog.setContentText(ConstantesPantallas.INTRODUCE_LA_NUEVA_PASSWORD_DE_LA_CARPETA + carpetaSeleccionada.getNombreCarpeta());
            dialog.showAndWait().ifPresent(nuevaPass -> {
                try {
                    viewModel.cambiarPasswordCarpeta(carpetaSeleccionada, nuevaPass);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(ConstantesPantallas.ERROR);
                    alert.setHeaderText(ConstantesPantallas.ERROR);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ConstantesPantallas.ERROR);
            alert.setHeaderText(ConstantesPantallas.ERROR);
            alert.setContentText(ConstantesPantallas.NO_HAY_NINGUNA_CARPETA_SELECCIONADA);
            alert.showAndWait();
        }
    }
}