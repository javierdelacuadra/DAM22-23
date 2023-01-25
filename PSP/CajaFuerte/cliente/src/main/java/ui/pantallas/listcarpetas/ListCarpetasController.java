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
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCarpeta"));
        columnaModo.setCellValueFactory(new PropertyValueFactory<>("modoEdicion"));
        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaContenido.setCellValueFactory(new PropertyValueFactory<>("contenido"));
        //TODO: cambiar acorde a los modelos
        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.error != null) {
                Platform.runLater(() -> getPrincipalController().createAlert(newState.error));
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
        Mensaje mensaje = new Mensaje();
        if (carpetaSeleccionada != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Añadir mensaje");
            dialog.setHeaderText("Añadir mensaje");
            dialog.setContentText("Introduce la contraseña de la carpeta " + carpetaSeleccionada.getNombreCarpeta());
            dialog.showAndWait().ifPresent(passCarpeta -> {
                try {
                    carpetaSeleccionada.setPassword(passCarpeta);
                    mensaje.setIDCarpeta(carpetaSeleccionada.getId());
                    mensaje.setContenido(textoMensaje.getText());
                    viewModel.addMensaje(mensaje, carpetaSeleccionada.getNombreCarpeta());
                    textoMensaje.clear();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No hay ninguna carpeta seleccionada");
            alert.showAndWait();
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
                    mensaje.setIDCarpeta(carpetaSeleccionada.getId());
                    mensaje.setContenido(textoMensaje.getText());
                    viewModel.updateMensaje(mensaje, carpetaSeleccionada.getNombreCarpeta());
                    textoMensaje.clear();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No hay ninguna carpeta seleccionada");
            alert.showAndWait();
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
                    mensaje.setIDCarpeta(carpetaSeleccionada.getId());
                    mensaje.setContenido(textoMensaje.getText());
                    viewModel.deleteMensaje(mensaje);
                    textoMensaje.clear();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No hay ninguna carpeta seleccionada");
            alert.showAndWait();
        }
    }

    public void rellenarTextoMensaje() {
        Mensaje mensajeSeleccionado = tablaMensajes.getSelectionModel().getSelectedItem();
        textoMensaje.setText(mensajeSeleccionado.getContenido());
    }

    //TODO: añadir función de cambiar contraseña de carpeta
    //TODO: añadir función de compartir carpeta

}