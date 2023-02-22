package org.cliente.ui.getAllJavaFX.principal;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.vavr.control.Either;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.cliente.domain.modelo.PersonaCliente;
import org.cliente.ui.getAllJavaFX.common.ConstantesPantallas;

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
    private MFXTableView<PersonaCliente> tblPersonas;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
//        addListenerTabla();
        getAllAsyncTask();
    }

    private void getAllAsyncTask() {
        root.setCursor(Cursor.WAIT);
        var task = new Task<Either<String, List<PersonaCliente>>>() {
            @Override
            protected Either<String, List<PersonaCliente>> call() {
                return principalViewModel.getAllAsyncTask();
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            root.setCursor(Cursor.DEFAULT);
            Either<String, List<PersonaCliente>> result = task.getValue();
            result.peek(personaClientesList -> {
                tblPersonas.getItems().clear();
                tblPersonas.getItems().addAll(personaClientesList);
            }).peekLeft(this::sacarAlertError);
        });
        task.setOnFailed(workerStateEvent -> {
            root.setCursor(Cursor.DEFAULT);
            sacarAlertError(task.getException().getMessage());
        });
        new Thread(task).start();
    }

    private void initializeTable() {
        MFXTableColumn<PersonaCliente> idPersonas = new MFXTableColumn<>(ConstantesPantallas.ID, true, Comparator.comparing(PersonaCliente::getId));
        MFXTableColumn<PersonaCliente> nombrePersonas = new MFXTableColumn<>(ConstantesPantallas.NOMBRE, true, Comparator.comparing(PersonaCliente::getNombre));
        MFXTableColumn<PersonaCliente> fechaColumn = new MFXTableColumn<>(ConstantesPantallas.NACIMIENTO, true, Comparator.comparing(PersonaCliente::getFechaNacimiento));
        MFXTableColumn<PersonaCliente> passColumn = new MFXTableColumn<>(ConstantesPantallas.PASSWORD, true, Comparator.comparing(PersonaCliente::getPassword));
        idPersonas.setRowCellFactory(personaCliente -> new MFXTableRowCell<>(PersonaCliente::getId));
        nombrePersonas.setRowCellFactory(personaCliente -> new MFXTableRowCell<>(PersonaCliente::getNombre));
        fechaColumn.setRowCellFactory(personaCliente -> new MFXTableRowCell<>(PersonaCliente::getFechaNacimiento));
        passColumn.setRowCellFactory(personaCliente -> new MFXTableRowCell<>(PersonaCliente::getPassword));
        tblPersonas.getTableColumns().addAll(idPersonas, nombrePersonas, fechaColumn, passColumn);
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
