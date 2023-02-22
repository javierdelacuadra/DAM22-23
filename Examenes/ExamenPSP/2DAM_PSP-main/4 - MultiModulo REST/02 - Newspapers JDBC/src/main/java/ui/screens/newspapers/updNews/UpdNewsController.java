package ui.screens.newspapers.updNews;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import model.Newspaper;
import model.Reader;
import ui.screens.common.BaseScreenController;

public class UpdNewsController extends BaseScreenController {

    @FXML
    private MFXTableView<Newspaper> tblNews;

    @FXML
    private MFXTableColumn<Newspaper> idColumn;
    @FXML
    private MFXTableColumn<Newspaper> nameColumn;
    @FXML
    private MFXTableColumn<Newspaper> releaseColumn;

    @FXML
    private MFXTextField txtName;

    @FXML
    private DatePicker dpkBirth;

    private UpdNewsViewModel updNewsViewModel;

    @Inject
    public UpdNewsController(UpdNewsViewModel updNewsViewModel) {
        this.updNewsViewModel = updNewsViewModel;
    }

    @Override
    public void principalCargado() {
        idColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getId));
        nameColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getName_newspaper));
        releaseColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getRelease_date));
        tblNews.setFooterVisible(false);
        nameColumn.setColumnResizable(true);
        addListener();
        updNewsViewModel.loadScreen();
    }

    private void addListener() {
        updNewsViewModel.getState().addListener((observableValue, updNewsState, updNewsStateNew) -> {
            if (updNewsStateNew.getError() != null){
                getPrincipalController().showAlertError(updNewsStateNew.getError());
                updNewsViewModel.clearMessages();

            }
            if (updNewsStateNew.getConfirmation() != null) {
                getPrincipalController().showAlertInformation(updNewsStateNew.getConfirmation());
                updNewsViewModel.clearMessages();
            }
            if (updNewsStateNew.getAllNewspapers() != null || (updNewsState.getAllNewspapers() != null && updNewsStateNew.getAllNewspapers().size() != updNewsState.getAllNewspapers().size())) {
                tblNews.getItems().clear();
                tblNews.getItems().addAll(updNewsStateNew.getAllNewspapers());
            }
            if (updNewsStateNew.getNewsSelected() != null){
                Newspaper newspaper = updNewsStateNew.getNewsSelected();
                txtName.setText(newspaper.getName_newspaper());
                dpkBirth.setValue(newspaper.getRelease_date());
            }
        });
        tblNews.getSelectionModel().selectionProperty().addListener((observableValue, selectedReader, selectedReaderNew) -> {
            if (selectedReaderNew != null && observableValue.getValue().size() != 0) {
                updNewsViewModel.getNewsDetails(selectedReaderNew.values().stream().findFirst().orElse(null), selectedReaderNew.keySet().stream().findFirst().orElse(-1));
            }
        });
    }

    public void updNews(){
        updNewsViewModel.updateReader(txtName.getText(), dpkBirth.getValue());
    }

}
