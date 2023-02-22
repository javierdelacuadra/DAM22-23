package ui.pantallas.listadociudades;

import dominio.modelo.Estado;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import dominio.modelo.Ciudad;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ListadoCiudadesController extends BasePantallaController implements Initializable {

    @FXML
    private ComboBox<String> cmbPaises;

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
        listadoCiudadesViewModel.getTodosLosPaises();
        getPrincipalController().activateMenu();
    }

    private void addListeners() {
        listadoCiudadesViewModel.getState().addListener((observableValue, listadoState, listadoStateNew) -> {
            if (listadoStateNew.getError() != null) {
                getPrincipalController().sacarAlertError(listadoStateNew.getError());
            }
            if (listadoStateNew.getNombrePaises() != null && listadoStateNew.getNombrePaises() != listadoState.getNombrePaises()) {
                cmbPaises.getItems().addAll(listadoStateNew.getNombrePaises());
                cmbPaises.setValue(ConstantesPantallas.SELECCIONE_UN_PAIS);
            }

            changeEstadosTable(listadoState, listadoStateNew);

            if (listadoStateNew.getCiudadesList() == null) {
                tblCiudades.getItems().clear();
            } else if (listadoStateNew.getCiudadesList() != listadoState.getCiudadesList()) {
                tblCiudades.getItems().clear();
                tblCiudades.getItems().addAll(listadoStateNew.getCiudadesList());
            }
        });

        tblEstados.getSelectionModel().selectionProperty().addListener((observableValue, estado, estadoNew) -> {
            if (estadoNew != null && observableValue.getValue().size() != 0) {
                listadoCiudadesViewModel.getCiudadesPorEstado(estadoNew.values().stream().findFirst().orElse(new Estado(-1, "", "")), cmbPaises.getSelectionModel().getSelectedItem());
            }
        });
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
        listadoCiudadesViewModel.getEstadosPorPais(cmbPaises.getSelectionModel().getSelectedItem());
    }
}

