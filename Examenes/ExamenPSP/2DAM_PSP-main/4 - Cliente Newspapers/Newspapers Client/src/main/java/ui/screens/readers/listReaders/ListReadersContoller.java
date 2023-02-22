package ui.screens.readers.listReaders;

import model.Newspaper;
import model.Reader;
import model.TypeArt;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ui.screens.common.BaseScreenController;
import ui.screens.common.ScreenConstants;

import java.time.LocalDate;
import java.util.Comparator;

public class ListReadersContoller extends BaseScreenController {

    @FXML
    private ComboBox cmbNews;

    @FXML
    private MFXTableView<Reader> tblReadersNews;
    @FXML
    private MFXTableView<Reader> tblReaders;

    @FXML
    private ComboBox cmbTypes;
    @FXML
    private MFXTableView<Reader> tblReadersTypes;

    ListReadersViewModel listReaderDBsViewModel;

    @Inject
    public ListReadersContoller(ListReadersViewModel listReaderDBsViewModel) {
        this.listReaderDBsViewModel = listReaderDBsViewModel;
    }

    @Override
    public void principalCargado() {

        MFXTableColumn<Reader> idColumn = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Reader::getId));
        MFXTableColumn<Reader> nameReaderDB = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Reader::getName));
        MFXTableColumn<Reader> birthReaderDB = new MFXTableColumn<>(ScreenConstants.BIRTH, true, Comparator.comparing(Reader::getBirthDate));

        idColumn.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getId));
        nameReaderDB.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getName));
        birthReaderDB.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getBirthDate));
        tblReaders.setFooterVisible(false);
        tblReaders.getTableColumns().addAll(idColumn, nameReaderDB, birthReaderDB);

        MFXTableColumn<Reader> idColumnFilter = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Reader::getId));
        MFXTableColumn<Reader> nameReaderDBFiltered = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Reader::getName));
        MFXTableColumn<Reader> birthColumnFiltered = new MFXTableColumn<>(ScreenConstants.BIRTH, true, Comparator.comparing(Reader::getBirthDate));

        idColumnFilter.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getId));
        nameReaderDBFiltered.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getName));
        birthColumnFiltered.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getBirthDate));
        tblReadersTypes.setFooterVisible(false);
        tblReadersTypes.getTableColumns().addAll(idColumnFilter, nameReaderDBFiltered, birthColumnFiltered);

        MFXTableColumn<Reader> idColumnRxN = new MFXTableColumn<>(ScreenConstants.ID, true, Comparator.comparing(Reader::getId));
        MFXTableColumn<Reader> nameReaderDBRxN = new MFXTableColumn<>(ScreenConstants.NAME, true, Comparator.comparing(Reader::getName));
        MFXTableColumn<Reader> birthColumnRxN = new MFXTableColumn<>(ScreenConstants.BIRTH, true, Comparator.comparing(Reader::getBirthDate));

        idColumnRxN.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getId));
        nameReaderDBRxN.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getName));
        birthColumnRxN.setRowCellFactory(reader -> new MFXTableRowCell<>(Reader::getBirthDate));
        tblReadersNews.setFooterVisible(false);
        tblReadersNews.getTableColumns().addAll(idColumnRxN, nameReaderDBRxN, birthColumnRxN);

        addListener();
        listReaderDBsViewModel.loadScreen();

    }


    private void addListener() {
        listReaderDBsViewModel.getState().addListener((observableValue, listadoState, listadoStateNew) -> {
            if (listadoStateNew.getError() != null) {
                getPrincipalController().showAlertError(listadoStateNew.getError());
            }
            if (listadoStateNew.getReaders() != null && listadoStateNew.getTypeArts() != null) {
                tblReaders.getItems().clear();
                tblReaders.getItems().addAll(listadoStateNew.getReaders());
                if (listadoStateNew.getTypeArts() != listadoState.getTypeArts()){
                    cmbTypes.getItems().clear();
                    cmbTypes.getItems().addAll(listadoStateNew.getTypeArts());
                    cmbTypes.setValue(listadoStateNew.getTypeArts().get(0));
                }
                if (listadoStateNew.getNewspaperList() != listadoState.getNewspaperList()){
                    cmbNews.getItems().clear();
                    cmbNews.getItems().addAll(listadoStateNew.getNewspaperList());
                    cmbNews.getSelectionModel().selectFirst();
                }
                if (listadoStateNew.getReadersFilteredType() != null && listadoStateNew.getReadersFilteredType() != listadoState.getReadersFilteredType()){
                    tblReadersTypes.getItems().clear();
                    tblReadersTypes.getItems().addAll(listadoStateNew.getReadersFilteredType());
                }
                if (listadoStateNew.getReadersFilteredNewspaper() != null && listadoStateNew.getReadersFilteredNewspaper() != listadoState.getReadersFilteredNewspaper()){
                    tblReadersNews.getItems().clear();
                    tblReadersNews.getItems().addAll(listadoStateNew.getReadersFilteredNewspaper());
                }

            }
            if(listadoStateNew.getReadersFilteredType() == null){
                tblReadersTypes.getItems().clear();
            }


            if(listadoStateNew.getReadersFilteredNewspaper() == null){
                tblReadersNews.getItems().clear();
            }

        });
    }

    public void changeType(ActionEvent actionEvent) {
        listReaderDBsViewModel.loadReadersByType(new TypeArt((cmbTypes.getSelectionModel().getSelectedIndex() + 1), cmbTypes.getSelectionModel().getSelectedItem().toString()));
    }

    public void changeNews(ActionEvent actionEvent) {
        listReaderDBsViewModel.loadReadersByNews(new Newspaper((cmbNews.getSelectionModel().getSelectedIndex() + 1), cmbNews.getSelectionModel().getSelectedItem().toString(), LocalDate.now()));
    }
}
