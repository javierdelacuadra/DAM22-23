package ui.pantallas.listcarpetas;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import model.Carpeta;
import model.Mensaje;
import ui.pantallas.common.BasePantallaController;

import java.util.List;

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
    private TableColumn<Mensaje, String> columnaAutor;

    public void initialize() {

    }

    public void cargarMensajes() {
        Carpeta carpetaSeleccionada = tablaCarpetas.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cargar mensajes");
        dialog.setHeaderText("Cargar mensajes");
        dialog.setContentText("Introduce la contraseña de la carpeta " + carpetaSeleccionada.getNombre());
        dialog.showAndWait().ifPresent(passCarpeta -> {
            try {
                List<Mensaje> mensajes = viewModel.cargarMensajes(passCarpeta);
                tablaMensajes.getItems().setAll(mensajes);
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