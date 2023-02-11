package ui.pantallas.listreaderscreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<Reader, String> cancellationDateColumn;

    @FXML
    public MFXComboBox<Newspaper> newspaperComboBox;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cancellationDateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item == null ? "No cancellation date" : item);
                }
            }
        });
        readersTable.setItems(viewModel.getReaders());
        newspaperComboBox.setItems(
                FXCollections.observableArrayList(viewModel.getNewspapers().stream().peek(newspaper -> newspaper.setName(newspaper.getName())).toList())
        );
    }


    public void filterByNewspaper() {
        Newspaper newspaper = newspaperComboBox.getSelectionModel().getSelectedItem();
        if (viewModel.getReadersByNewspaper(newspaper).isRight()) {
            readersTable.setItems(FXCollections.observableArrayList(viewModel.getReadersByNewspaper(newspaper).get()));
        } else {
            readersTable.setItems(viewModel.getReaders());
            this.getPrincipalController().createAlert(ConstantesUI.COULDN_T_FIND_ANY_READER_WITH_THAT_NEWSPAPER);
        }
    }

    public void resetFilters() {
        readersTable.setItems(viewModel.getReaders());
        newspaperComboBox.getSelectionModel().clearSelection();
    }
}