package ui.screens.newspapers.addNews;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import model.Newspaper;
import ui.screens.common.BaseScreenController;

public class AddNewsController extends BaseScreenController {

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

    private AddNewsViewModel addNewsViewModel;

    @Inject
    public AddNewsController(AddNewsViewModel addNewsViewModel) {
        this.addNewsViewModel = addNewsViewModel;
    }

    @Override
    public void principalCargado() {
        idColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getId));
        nameColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getName_newspaper));
        releaseColumn.setRowCellFactory(newspaper -> new MFXTableRowCell<>(Newspaper::getRelease_date));
        tblNews.setFooterVisible(false);
        nameColumn.setColumnResizable(true);
        addListener();
        addNewsViewModel.loadScreen();
    }

    private void addListener() {
        addNewsViewModel.getState().addListener((observableValue, addReaderState, addReaderStateNew) -> {
            if (addReaderStateNew.getError() != null){
                getPrincipalController().showAlertError(addReaderStateNew.getError());
            }
            if (addReaderStateNew.getWelcomeMessage() != null) {
                getPrincipalController().showAlertInformation(addReaderStateNew.getWelcomeMessage());
            }
            if (addReaderStateNew.getAllNews() != null || (addReaderState.getAllNews() != null && addReaderStateNew.getAllNews().size() != addReaderState.getAllNews().size())) {
                tblNews.getItems().clear();
                tblNews.getItems().addAll(addReaderStateNew.getAllNews());
            }
            addNewsViewModel.clearState();
        });
    }

    public void addNews(){
        addNewsViewModel.addNews(new Newspaper(txtName.getText(), dpkBirth.getValue()));
    }

}
