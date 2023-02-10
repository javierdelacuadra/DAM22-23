package ui.pantallas.listreaderscreen;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
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
    private TableColumn<Reader, String> cancellationDateColumn;

    @FXML
    public MFXComboBox<Newspaper> newspaperComboBox;

    @FXML
    public MFXComboBox<ArticleType> articleTypeComboBox;

    @FXML
    public TableView<String> ratingsTable;

    @FXML
    public TableColumn<String, String> newspapernameColumn;

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
        articleTypeComboBox.setItems(
                FXCollections.observableArrayList(viewModel.getArticleTypes().stream().peek(articleType -> articleType.setDescription(articleType.getDescription())).toList())
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

    public void filterByArticleType() {
        ArticleType articleType = articleTypeComboBox.getSelectionModel().getSelectedItem();
        if (viewModel.getReadersByArticleType(articleType).isRight()) {
            readersTable.setItems(viewModel.getReadersByArticleType(articleType).get());
        } else {
            readersTable.setItems(viewModel.getReaders());
            this.getPrincipalController().createAlert(ConstantesUI.COULDN_T_FIND_ANY_READER_WITH_THAT_ARTICLE_TYPE);
        }
    }

    public void getAvgRatingByReader() {
//        Reader reader = readersTable.getSelectionModel().getSelectedItem();
//        if (reader != null) {
//            Map<Double, String> avgRatings = viewModel.getAvgRating(reader.getId());
//            if (!avgRatings.isEmpty()) {
//                List<String> dataList = new ArrayList<>();
//                for (Map.Entry<Double, String> entry : avgRatings.entrySet()) {
//                    dataList.add(entry.getValue() + " - " + String.format("%.2f", entry.getKey()));
//                }
//                ratingsTable.setItems(FXCollections.observableArrayList(dataList));
//                newspapernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
//            } else {
//                this.getPrincipalController().createAlert("The reader " + reader.getName() + " hasn't rated any article yet");
//            }
//        } else {
//            this.getPrincipalController().createAlert("Please select a reader");
//        }
    }

    public void resetFilters() {
        readersTable.setItems(viewModel.getReaders());
        newspaperComboBox.getSelectionModel().clearSelection();
        articleTypeComboBox.getSelectionModel().clearSelection();
    }
}