package ui.screens.newspapers.updnews;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.DatePicker;
import org.miutils.domain.modelo.Newspaper;
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

    private final UpdNewsViewModel updNewsViewModel;

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
            asyncChanges(updNewsState, updNewsStateNew);
            if (updNewsStateNew.getNewsSelected() != null) {
                Newspaper newspaper = updNewsStateNew.getNewsSelected();
                txtName.setText(newspaper.getName_newspaper());
                dpkBirth.setValue(newspaper.getRelease_date());
            }

        });
        tblListener();
    }

    private void asyncChanges(UpdNewsState updNewsState, UpdNewsState updNewsStateNew) {
        Platform.runLater(() -> {

            if (updNewsStateNew.isLoading()) {
                getPrincipalController().root.setCursor(Cursor.WAIT);
            } else {
                getPrincipalController().root.setCursor(Cursor.DEFAULT);
            }

            if (updNewsStateNew.getAllNewspapers() != null || (updNewsState.getAllNewspapers() != null && updNewsStateNew.getAllNewspapers().size() != updNewsState.getAllNewspapers().size())) {
                tblNews.getItems().clear();
                tblNews.getItems().addAll(updNewsStateNew.getAllNewspapers());
            }

            if (updNewsStateNew.getError() != null) {
                getPrincipalController().showAlertError(updNewsStateNew.getError());
                updNewsViewModel.clearMessages();

            }
            if (updNewsStateNew.getConfirmation() != null) {
                getPrincipalController().showAlertInformation(updNewsStateNew.getConfirmation());
                updNewsViewModel.clearMessages();
            }

        });
    }

    private void tblListener() {
        tblNews.getSelectionModel().selectionProperty().addListener((observableValue, selectedNews, selectedNewsNew) -> {
            if (selectedNewsNew != null && observableValue.getValue().size() != 0) {
                updNewsViewModel.getNewsDetails(selectedNewsNew.values().stream().findFirst().orElse(null), selectedNewsNew.keySet().stream().findFirst().orElse(-1));
            }
        });
    }

    public void addNews() {
        updNewsViewModel.addNews(new Newspaper(txtName.getText(), dpkBirth.getValue()));
    }

    public void updNews() {
        updNewsViewModel.updateNews(txtName.getText(), dpkBirth.getValue());
    }

}
