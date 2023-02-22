package ui.pantallas.paisdetalle;

import dominio.modelo.Pais;
import dominio.modelo.PaisDetalle;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PaisDetalleController extends BasePantallaController implements Initializable {

    private final PaisDetalleViewModel paisDetalleViewModel;

    @Inject
    public PaisDetalleController(PaisDetalleViewModel paisDetalleViewModel) {
        this.paisDetalleViewModel = paisDetalleViewModel;
    }

    @FXML
    MFXTableView<Pais> tblPaises;

    @FXML
    Text txtInfoNombre;

    @FXML
    Text txtInfoContinente;

    @FXML
    Text txtInfoCapital;

    @FXML
    Text txtInfoMoneda;

    @FXML
    Text txtInfoTelefono;

    @FXML
    Text txtInfoDominio;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MFXTableColumn<Pais> idPais = new MFXTableColumn<>(ConstantesPantallas.ID, true, Comparator.comparing(Pais::id));
        MFXTableColumn<Pais> nombrePais = new MFXTableColumn<>(ConstantesPantallas.NOMBRE, true, Comparator.comparing(Pais::nombre));
        MFXTableColumn<Pais> isoColumn = new MFXTableColumn<>(ConstantesPantallas.ISO_2, true, Comparator.comparing(Pais::iso2));
        idPais.setRowCellFactory(pais -> new MFXTableRowCell<>(Pais::id));
        nombrePais.setRowCellFactory(pais -> new MFXTableRowCell<>(Pais::nombre));
        isoColumn.setRowCellFactory(pais -> new MFXTableRowCell<>(Pais::iso2));
        tblPaises.getTableColumns().addAll(idPais, nombrePais, isoColumn);
        tblPaises.setFooterVisible(false);
        tblPaises.getSelectionModel().setAllowsMultipleSelection(false);
    }

    @Override
    public void principalCargado() {
        addListenerTabla();
        paisDetalleViewModel.loadPaises();
    }

    private void addListenerTabla() {
        paisDetalleViewModel.getState().addListener((observableValue, paisDetalleState, paisDetalleStateNew) -> {
            if (paisDetalleStateNew.getError() != null) {
                getPrincipalController().sacarAlertError(paisDetalleStateNew.getError());
            }
            if (paisDetalleStateNew.getPaisesList() != null) {
                tblPaises.getItems().clear();
                tblPaises.getItems().addAll(paisDetalleStateNew.getPaisesList());
            }
            if (paisDetalleStateNew.getPaisDetalle() != paisDetalleState.getPaisDetalle() && paisDetalleStateNew.getPaisDetalle() != null) {
                PaisDetalle pais = paisDetalleStateNew.getPaisDetalle();
                txtInfoNombre.setText(pais.nombre());
                txtInfoCapital.setText(pais.capital());
                txtInfoContinente.setText(pais.region());
                txtInfoMoneda.setText(pais.moneda());
                txtInfoTelefono.setText(pais.telefono());
                txtInfoDominio.setText(pais.dominio());
            }
        });


        tblPaises.getSelectionModel().selectionProperty().addListener((observableValue, paisDetalle, paisDetalleNew) -> {
            if (paisDetalleNew != null && observableValue.getValue().size() != 0) {
                paisDetalleViewModel.getPaisDetalle(paisDetalleNew.values().stream().findFirst().orElse(new Pais(-1, "", "")).iso2());
            }
        });
    }

}
