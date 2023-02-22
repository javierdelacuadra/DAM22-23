package ui.pantallas.paisdetalle;

import dominio.modelo.Pais;
import dominio.modelo.PaisDetalle;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
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
    private MFXTableView<Pais> tblPaises;

    @FXML
    private Text txtInfoNombre;

    @FXML
    private Text txtInfoContinente;

    @FXML
    private Text txtInfoCapital;

    @FXML
    private Text txtInfoMoneda;

    @FXML
    private Text txtInfoTelefono;

    @FXML
    private Text txtInfoDominio;


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
        paisDetalleViewModel.loadPaisesSingleRetrofit();
    }

    private void addListenerTabla() {
        paisDetalleViewModel.getState().addListener((observableValue, paisDetalleState, paisDetalleStateNew) ->
                Platform.runLater(() -> {
                    if (paisDetalleStateNew.isLoading()) {
                        getPrincipalController().root.setCursor(Cursor.WAIT);
                    } else {
                        getPrincipalController().root.setCursor(Cursor.DEFAULT);
                    }
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
                }));


        tblPaises.getSelectionModel().selectionProperty().addListener((observableValue, paisDetalle, paisDetalleNew) ->
                getPaisesDetalle(observableValue, paisDetalleNew));
    }

    private void getPaisesDetalle(ObservableValue<? extends ObservableMap<Integer, Pais>> observableValue, ObservableMap<Integer, Pais> paisDetalleNew) {
        if (paisDetalleNew != null && observableValue.getValue().size() != 0) {
            paisDetalleViewModel.loadPaisDetalleSingleRetrofit(paisDetalleNew.values().stream().findFirst().orElse(new Pais(-1, "", "")).iso2());
        }
    }

}
