package ui.pantallas.listadociudades;

import dominio.modelo.Estado;
import dominio.modelo.Pais;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import dominio.modelo.Ciudad;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ListadoCiudadesController extends BasePantallaController implements Initializable {

    @FXML
    private ComboBox<Pais> cmbPaises;

    @FXML
    private MFXTableView<Ciudad> tblCiudades;
    @FXML
    private MFXTableView<Estado> tblEstados;


    ListadoCiudadesViewModel listadoCiudadesViewModel;


    @Inject
    public ListadoCiudadesController(ListadoCiudadesViewModel listadoCiudadesViewModel) {
        this.listadoCiudadesViewModel = listadoCiudadesViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MFXTableColumn<Estado> idEstado = new MFXTableColumn<>(ConstantesPantallas.ID, true, Comparator.comparing(Estado::id));
        MFXTableColumn<Estado> nombreEstado = new MFXTableColumn<>(ConstantesPantallas.NOMBRE, true, Comparator.comparing(Estado::nombre));
        MFXTableColumn<Estado> isoColumn = new MFXTableColumn<>(ConstantesPantallas.ISO_2, true, Comparator.comparing(Estado::iso2));
        idEstado.setRowCellFactory(estado -> new MFXTableRowCell<>(Estado::id));
        nombreEstado.setRowCellFactory(estado -> new MFXTableRowCell<>(Estado::nombre));
        isoColumn.setRowCellFactory(estado -> new MFXTableRowCell<>(Estado::iso2));
        nombreEstado.setPrefWidth(200);
        tblEstados.getTableColumns().addAll(idEstado, nombreEstado, isoColumn);
        tblEstados.setFooterVisible(false);
        tblEstados.getSelectionModel().setAllowsMultipleSelection(false);

        MFXTableColumn<Ciudad> id = new MFXTableColumn<>(ConstantesPantallas.ID, true);
        MFXTableColumn<Ciudad> nombre = new MFXTableColumn<>(ConstantesPantallas.NOMBRE, true);
        id.setRowCellFactory(ciudad -> new MFXTableRowCell<>(Ciudad::id));
        nombre.setRowCellFactory(ciudad -> new MFXTableRowCell<>(Ciudad::nombre));
        nombre.setPrefWidth(200);
        tblCiudades.getTableColumns().addAll(id, nombre);
        tblCiudades.setFooterVisible(false);
        tblCiudades.getSelectionModel().setAllowsMultipleSelection(false);
    }

    @Override
    public void principalCargado() {
        addListeners();
        getPrincipalController().activateMenu();
        listadoCiudadesViewModel.loadPaisesSingleRetrofit();
    }

    private void addListeners() {
        listadoCiudadesViewModel.getState().addListener((observableValue, listadoState, listadoStateNew) -> {
            // Es lo único que se ejecuta con llamadas single desde retrofit
            Platform.runLater(() -> {
                if (listadoStateNew.isLoading()) {
                    getPrincipalController().root.setCursor(Cursor.WAIT);
                }
                else {
                    getPrincipalController().root.setCursor(Cursor.DEFAULT);
                }
                if (listadoStateNew.getError() != null) {
                    getPrincipalController().sacarAlertError(listadoStateNew.getError());
                }
                if (listadoStateNew.getPaisesList() != null && listadoStateNew.getPaisesList() != listadoState.getPaisesList()) {
                    cmbPaises.getItems().addAll(listadoStateNew.getPaisesList());
                }
            });

            changeEstadosTable(listadoState, listadoStateNew);

            changeCiudadesTable(listadoState, listadoStateNew);
        });

        tblEstados.getSelectionModel().selectionProperty().addListener((observableValue, estado, estadoNew) -> {
            if (estadoNew != null && observableValue.getValue().size() != 0) {
                ciudadesPorEstadoAsyncConTask(estadoNew.values().stream().findFirst().orElse(new Estado(-1, "", "")), cmbPaises.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void changeCiudadesTable(ListadoCiudadesState listadoState, ListadoCiudadesState listadoStateNew) {
        if (listadoStateNew.getCiudadesList() == null) {
            tblCiudades.getItems().clear();
        } else if (listadoStateNew.getCiudadesList() != listadoState.getCiudadesList()) {
            tblCiudades.getItems().clear();
            tblCiudades.getItems().addAll(listadoStateNew.getCiudadesList());
        }
    }

    private void changeEstadosTable(ListadoCiudadesState listadoState, ListadoCiudadesState listadoStateNew) {
        if (listadoStateNew.getEstadoList() == null) {
            tblEstados.getItems().clear();
        } else if (listadoStateNew.getEstadoList() != listadoState.getEstadoList()) {
            tblEstados.getItems().clear();
            tblEstados.getItems().addAll(listadoStateNew.getEstadoList());
            tblCiudades.getItems().clear();
        }
    }

    public void paisSelected() {
        estadosPorPaisAsyncConSingle(cmbPaises.getSelectionModel().getSelectedItem());
    }

    // Asincronismo con Single en UI
    private void estadosPorPaisAsyncConSingle(Pais pais) {
        Single.just(listadoCiudadesViewModel.getEstadosPorPaisAsyncConSingleEnUI(pais))
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> getPrincipalController().root.setCursor(Cursor.DEFAULT))
                .subscribe(result ->
                                result.peek(estados -> {
                                            tblEstados.getItems().clear();
                                            tblEstados.getItems().addAll(estados);
                                        })
                                        .peekLeft(error -> getPrincipalController().sacarAlertError(error)),
                        throwable -> getPrincipalController().sacarAlertError(throwable.getMessage()));

        getPrincipalController().root.setCursor(Cursor.WAIT);
    }

    // Asincronismo con Task
    private void ciudadesPorEstadoAsyncConTask(Estado estado, Pais pais) {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        var task = new Task<Either<String, List<Ciudad>>>() {
            @Override
            protected Either<String, List<Ciudad>> call() {
                return listadoCiudadesViewModel.getCiudadesPorEstadoAsyncConTask(estado, pais);
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            getPrincipalController().root.setCursor(Cursor.DEFAULT);
            Either<String, List<Ciudad>> result = task.getValue();
            result.peek(ciudades -> {
                tblCiudades.getItems().clear();
                tblCiudades.getItems().addAll(ciudades);
            }).peekLeft(error -> getPrincipalController().sacarAlertError(error));
        });
        task.setOnFailed(workerStateEvent -> {
            getPrincipalController().root.setCursor(Cursor.DEFAULT);
            getPrincipalController().sacarAlertError(task.getException().getMessage());
        });
        new Thread(task).start();
    }
}
