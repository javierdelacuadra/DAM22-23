package ui.pantallas.listreaderscreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ArticleType;
import model.Newspaper;
import model.Reader;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

public class ListReadersScreenController extends BasePantallaController {

    private final ListReadersScreenViewModel viewModel;

    @Inject
    public ListReadersScreenController(ListReadersScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Reader> readersTable;

    @FXML
    private TableColumn<Reader, Integer> idColumn;

    @FXML
    private TableColumn<Reader, String> nameColumn;

    @FXML
    private TableColumn<Reader, String> birthDateColumn;

    @FXML
    public MFXComboBox<Newspaper> newspaperComboBox;

    @FXML
    public MFXComboBox<ArticleType> articleTypeComboBox;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        readersTable.setItems(viewModel.getReaders());
        newspaperComboBox.setItems(viewModel.getNewspapers());
        articleTypeComboBox.setItems(viewModel.getArticleTypes());
    }


    public void filterByNewspaper() {
        Newspaper newspaper = newspaperComboBox.getSelectionModel().getSelectedItem();
        if (viewModel.getReadersByNewspaper(newspaper).isRight()) {
            readersTable.setItems(viewModel.getReadersByNewspaper(newspaper).get());
        } else {
            readersTable.setItems(viewModel.getReaders());
            this.getPrincipalController().createAlert(ConstantesUI.COULDN_T_FIND_ANY_READER_WITH_THAT_NEWSPAPER);
        }
    }

    public void filterByArticleType() {
        String articleType = articleTypeComboBox.getSelectionModel().getSelectedItem().getDescription();
        if (viewModel.getReadersByArticleType(articleType).isRight()) {
            readersTable.setItems(viewModel.getReadersByArticleType(articleType).get());
        } else {
            readersTable.setItems(viewModel.getReaders());
            this.getPrincipalController().createAlert(ConstantesUI.COULDN_T_FIND_ANY_READER_WITH_THAT_ARTICLE_TYPE);
        }
    }

    public void filterOldestSubscribers() {
        if (viewModel.getOldestSubscribers().isRight()) {
            readersTable.setItems(viewModel.getOldestSubscribers().get());
        } else {
            readersTable.setItems(viewModel.getReaders());
            this.getPrincipalController().createAlert(ConstantesUI.COULDN_T_FIND_ANY_READER_SUBSCRIBED_TO_EL_HOLA_MUNDO);
        }
    }

    public void resetFilters() {
        readersTable.setItems(viewModel.getReaders());
        newspaperComboBox.getSelectionModel().clearSelection();
        articleTypeComboBox.getSelectionModel().clearSelection();
    }
}