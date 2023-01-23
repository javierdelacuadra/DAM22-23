package ui.pantallas.listcarpetas;

import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Carpeta;
import modelo.Mensaje;
import ui.pantallas.common.BasePantallaController;

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
    private TableColumn<Mensaje, String> columnaContenido;

    @FXML
    private TableColumn<Mensaje, String> columnaIDMensaje;

    @FXML
    private MFXTextField textoMensaje;

    public void initialize() {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCarpeta"));
        columnaModo.setCellValueFactory(new PropertyValueFactory<>("modoEdicion"));
        columnaContenido.setCellValueFactory(new PropertyValueFactory<>("contenido"));
        columnaIDMensaje.setCellValueFactory(new PropertyValueFactory<>("id"));
        //TODO: cambiar acorde a los modelos
        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error != null) {
                Platform.runLater(() -> {
                    getPrincipalController().createAlert(newState.error);
                });
            }
            if (newState.carpetas != null) {
                Platform.runLater(() -> {
                    tablaCarpetas.getItems().clear();
                    tablaCarpetas.getItems().addAll(newState.carpetas);
                });
            }
            if (newState.mensajes != null) {
                Platform.runLater(() -> {
                    tablaMensajes.getItems().clear();
                    tablaMensajes.getItems().addAll(newState.mensajes);
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
        dialog.setTitle("Cargar mensajes");
        dialog.setHeaderText("Cargar mensajes");
        dialog.setContentText("Introduce la contraseña de la carpeta " + carpetaSeleccionada.getNombreCarpeta());
        dialog.showAndWait().ifPresent(passCarpeta -> {
            try {
                carpetaSeleccionada.setPassword(passCarpeta);
                viewModel.cargarMensajes(carpetaSeleccionada);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }


    public void addMensaje() {
        Carpeta carpetaSeleccionada = tablaCarpetas.getSelectionModel().getSelectedItem();
        Mensaje mensaje = tablaMensajes.getSelectionModel().getSelectedItem();
        if (carpetaSeleccionada != null && mensaje != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Añadir mensaje");
            dialog.setHeaderText("Añadir mensaje");
            dialog.setContentText("Introduce la contraseña de la carpeta " + carpetaSeleccionada.getNombreCarpeta());
            dialog.showAndWait().ifPresent(passCarpeta -> {
                try {
                    carpetaSeleccionada.setPassword(passCarpeta);
                    viewModel.addMensaje(mensaje);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        }
    }

    public void updateMensaje() {
        Carpeta carpetaSeleccionada = tablaCarpetas.getSelectionModel().getSelectedItem();
        Mensaje mensaje = tablaMensajes.getSelectionModel().getSelectedItem();
        if (carpetaSeleccionada != null && mensaje != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modificar mensaje");
            dialog.setHeaderText("Modificar mensaje");
            dialog.setContentText("Introduce la contraseña de la carpeta " + carpetaSeleccionada.getNombreCarpeta());
            dialog.showAndWait().ifPresent(passCarpeta -> {
                try {
                    carpetaSeleccionada.setPassword(passCarpeta);
                    viewModel.updateMensaje(mensaje);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        }
    }

    public void deleteMensaje() {
        Carpeta carpetaSeleccionada = tablaCarpetas.getSelectionModel().getSelectedItem();
        Mensaje mensaje = tablaMensajes.getSelectionModel().getSelectedItem();
        if (carpetaSeleccionada != null && mensaje != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Eliminar mensaje");
            dialog.setHeaderText("Eliminar mensaje");
            dialog.setContentText("Introduce la contraseña de la carpeta " + carpetaSeleccionada.getNombreCarpeta());
            dialog.showAndWait().ifPresent(passCarpeta -> {
                try {
                    carpetaSeleccionada.setPassword(passCarpeta);
                    viewModel.deleteMensaje(mensaje);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        }
    }

    public void rellenarTextoMensaje() {
        Mensaje mensajeSeleccionado = tablaMensajes.getSelectionModel().getSelectedItem();
        textoMensaje.setText(mensajeSeleccionado.getContenido());
    }
}