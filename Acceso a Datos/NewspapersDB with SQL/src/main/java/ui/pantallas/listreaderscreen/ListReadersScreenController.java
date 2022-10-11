package ui.pantallas.listreaderscreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Newspaper;
import modelo.Reader;
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

    public void initialize() throws JAXBException {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        readersTable.setItems(viewModel.getReaders());
        newspaperComboBox.setItems(viewModel.getNewspapers());
    }


    public void filterByNewspaper() throws JAXBException {
        Newspaper newspaper = newspaperComboBox.getSelectionModel().getSelectedItem();
        readersTable.setItems(viewModel.getReadersByNewspaper(newspaper));
    }
}