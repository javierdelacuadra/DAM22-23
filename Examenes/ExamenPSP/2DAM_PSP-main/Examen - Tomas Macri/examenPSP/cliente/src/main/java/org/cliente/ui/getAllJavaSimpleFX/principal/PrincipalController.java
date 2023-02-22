package org.cliente.ui.getAllJavaSimpleFX.principal;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.vavr.control.Either;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.cliente.ui.getAllJavaSimpleFX.common.ConstantesPantallas;
import org.utils.domain.modelo.Equipo;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    // objeto especial para DI
    Instance<Object> instance;

    private final PrincipalViewModel principalViewModel;

    @Inject
    public PrincipalController(Instance<Object> instance, PrincipalViewModel principalViewModel) {
        this.instance = instance;
        this.principalViewModel = principalViewModel;
    }

    @FXML
    private BorderPane root;
    private Stage primaryStage;

    @FXML
    private MFXTableView<Equipo> tblPersonas;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        getAllAsyncTask();
    }

    private void getAllAsyncTask() {
        root.setCursor(Cursor.WAIT);
        var task = new Task<Either<String, List<Equipo>>>() {
            @Override
            protected Either<String, List<Equipo>> call() {
                return principalViewModel.getAllAsyncTask();
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            root.setCursor(Cursor.DEFAULT);
            Either<String, List<Equipo>> result = task.getValue();
            result.peek(equipos -> {
                tblPersonas.getItems().clear();
                tblPersonas.getItems().addAll(equipos);
            }).peekLeft(this::sacarAlertError);
        });
        task.setOnFailed(workerStateEvent -> {
            root.setCursor(Cursor.DEFAULT);
            sacarAlertError(task.getException().getMessage());
        });
        new Thread(task).start();
    }

    private void initializeTable() {
        MFXTableColumn<Equipo> nombreCol = new MFXTableColumn<>("Nombre", true, Comparator.comparing(Equipo::getNombre));
        MFXTableColumn<Equipo> entrenadorCol = new MFXTableColumn<>("Entrenador", true, Comparator.comparing(Equipo::getEntrenador));
        nombreCol.setRowCellFactory(personaCliente -> new MFXTableRowCell<>(Equipo::getNombre));
        entrenadorCol.setRowCellFactory(personaCliente -> new MFXTableRowCell<>(Equipo::getEntrenador));
        tblPersonas.getTableColumns().addAll(nombreCol, entrenadorCol);
        tblPersonas.setFooterVisible(false);
        tblPersonas.getSelectionModel().setAllowsMultipleSelection(false);
    }

    // COSAS DEL PRINCIPAL

    private void sacarAlertError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(error);
        alert.setResizable(true);
        alert.showAndWait();
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
}
