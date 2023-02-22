package ui.screens.suscriptions.changeSuscription;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.Newspaper;
import ui.screens.common.BaseScreenController;

import java.util.ArrayList;
import java.util.Objects;

public class ChangeSuscriptionsController extends BaseScreenController {
    @FXML
    private MFXTableColumn<Newspaper> idColumnUn;
    @FXML
    private MFXTableColumn<Newspaper> nameColumnUn;
    @FXML
    private MFXTableColumn<Newspaper> releaseColumnUn;

    @FXML
    private MFXTableView<Newspaper> tblNewsUnsusc;
    @FXML
    private MFXTableColumn<Newspaper> idColumnSusc;
    @FXML
    private MFXTableColumn<Newspaper> nameColumnSusc;
    @FXML
    private MFXTableColumn<Newspaper> releaseColumnSusc;

    @FXML
    private MFXTableView<Newspaper> tblNewsSusc;


    private ChangeSuscriptionsViewModel csViewModel;

    @Inject
    public ChangeSuscriptionsController(ChangeSuscriptionsViewModel csViewModel) {
        this.csViewModel = csViewModel;
    }


    @Override
    public void principalCargado() {
        initializeMFXColumns(idColumnSusc, nameColumnSusc, releaseColumnSusc, tblNewsSusc);
        initializeMFXColumns(idColumnUn, nameColumnUn, releaseColumnUn, tblNewsUnsusc);
        addListeners();
        csViewModel.loadScreen(getPrincipalController().getActualUser());

    }

    private void addListeners() {
        csViewModel.getState().addListener((observableValue, changeSuscriptionsState, changeSuscriptionsStateNew) -> {
            if (changeSuscriptionsStateNew.getNewsNotSuscribed() != null) {
                tblNewsUnsusc.getItems().clear();
                tblNewsUnsusc.getItems().addAll(changeSuscriptionsStateNew.getNewsNotSuscribed());
            }
            if (changeSuscriptionsStateNew.getNewsSuscribed() != null) {
                tblNewsSusc.getItems().clear();
                tblNewsSusc.getItems().addAll(changeSuscriptionsStateNew.getNewsSuscribed());
            }
            if (changeSuscriptionsStateNew.getNewspaperSelected() != null) {
                if (Boolean.TRUE.equals(changeSuscriptionsStateNew.getIsSuscribedToNewsSelected())) {
                    tblNewsUnsusc.getSelectionModel().deselectItems();
                } else {
                    tblNewsSusc.getSelectionModel().deselectItems();
                }
            }
            if (changeSuscriptionsStateNew.getError() != null) {
                getPrincipalController().showAlertError(changeSuscriptionsStateNew.getError());
                csViewModel.clearMessages();

            }
            if (changeSuscriptionsStateNew.getSuccess() != null) {
                getPrincipalController().showAlertInformation(changeSuscriptionsStateNew.getSuccess());
                csViewModel.clearMessages();
            }
        });
        tblNewsSusc.getSelectionModel().selectionProperty().
                addListener((observableValue, oldSelection, newSelection) ->
                {
                    if (newSelection != null) {
                        csViewModel.getNewsSelected(newSelection.values().stream().findFirst().orElse(null), true);
                    }
                });
        tblNewsUnsusc.getSelectionModel().selectionProperty().
                addListener((observableValue, oldSelection, newSelection) ->
                {
                    if (newSelection != null) {
                        csViewModel.getNewsSelected(newSelection.values().stream().findFirst().orElse(null), false);
                    }
                });
    }

    public void addOrDeleteSubscription(ActionEvent actionEvent) {
        csViewModel.changeNewsState();
    }

    private void initializeMFXColumns(MFXTableColumn<Newspaper> idColumnUn, MFXTableColumn<Newspaper> nameColumnUn, MFXTableColumn<Newspaper> releaseColumnUn, MFXTableView<Newspaper> tblNewsUnsusc) {
        idColumnUn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getId));
        nameColumnUn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getName_newspaper));
        releaseColumnUn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getRelease_date));
        nameColumnUn.setColumnResizable(true);
        tblNewsUnsusc.setFooterVisible(false);
    }

}
