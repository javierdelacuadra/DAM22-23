package ui.pantallas.pantallaequipos;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Equipo;
import servicios.ServiciosJugadores;
import ui.common.BasePantallaController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PantallaEquiposController extends BasePantallaController implements Initializable {

    private final ServiciosJugadores servicios;

    @Inject
    public PantallaEquiposController(ServiciosJugadores servicios) {
        this.servicios = servicios;
    }

    @FXML
    private TableView<Equipo> equiposTable;

    @FXML
    private TableColumn<Equipo, String> columnaNombre;

    @FXML
    private TableColumn<Equipo, String> columnaEntrenador;

    @FXML
    private TableColumn<Equipo, String> releaseDateColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaEntrenador.setCellValueFactory(new PropertyValueFactory<>("entrenador"));
    }

    public void cargarEquipos() {
        var task = new Task<Either<String, List<Equipo>>>() {
            @Override
            protected Either<String, List<Equipo>> call() {
                return servicios.verTodosLosEquiposSinJugadores();
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            getPrincipalController().root.setCursor(Cursor.DEFAULT);
            var result = task.getValue();
            result.peek(equipos -> {
                equiposTable.setItems(FXCollections.observableArrayList(
                        servicios.verTodosLosEquiposSinJugadores().get()));
            }).peekLeft(error -> getPrincipalController().createAlert(error));
        });
        task.setOnFailed(workerStateEvent -> {
            getPrincipalController().createAlert(task.getException().getMessage());
            getPrincipalController().root.setCursor(Cursor.DEFAULT);
        });
        new Thread(task).start();
    }
}
