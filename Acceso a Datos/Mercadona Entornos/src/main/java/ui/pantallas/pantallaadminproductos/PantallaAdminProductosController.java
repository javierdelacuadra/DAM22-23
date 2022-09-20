package ui.pantallas.pantallaadminproductos;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Producto;
import modelo.ProductoNormal;
import ui.pantallas.common.BasePantallaController;

import java.net.URL;
import java.util.ResourceBundle;

public class PantallaAdminProductosController extends BasePantallaController implements Initializable {

    private final PantallaAdminProductosViewModel viewModel;

    @Inject
    public PantallaAdminProductosController(PantallaAdminProductosViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> columnaNombre;

    @FXML
    private TableColumn<Producto, String> columnaPrecio;

    @FXML
    private TableColumn<Producto, String> columnaStock;

    @FXML
    private MFXTextField textoNombre;

    @FXML
    private MFXTextField textoPrecio;

    @FXML
    private MFXTextField textoStock;

    @FXML
    private MFXButton botonadd;

    @FXML
    private MFXButton botonupdate;

    @FXML
    private MFXButton botonremove;

    public void addProducto() {
        if (!textoNombre.getText().isEmpty() && !textoPrecio.getText().isEmpty() && !textoStock.getText().isEmpty()) {
            Producto p = new ProductoNormal(textoNombre.getText(), Double.parseDouble(textoPrecio.getText()), Integer.parseInt(textoStock.getText()));
            viewModel.addProducto(p);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al añadir el producto");
            alert.setContentText("No se han introducido todos los datos");
            alert.showAndWait();
        }
    }

    public void removeProducto() {
        if (tablaProductos.getSelectionModel().getSelectedItem() != null) {
            Producto p = tablaProductos.getSelectionModel().getSelectedItem();
            viewModel.removeProducto(p);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al eliminar el producto");
            alert.setContentText("No se ha seleccionado ningún producto");
            alert.showAndWait();
        }
    }

    public void updateProducto() {
        if (tablaProductos.getSelectionModel().getSelectedItem() != null && !textoNombre.getText().isEmpty() && !textoPrecio.getText().isEmpty() && !textoStock.getText().isEmpty()) {
            Producto p1 = new ProductoNormal(textoNombre.getText(), Double.parseDouble(textoPrecio.getText()), Integer.parseInt(textoStock.getText()));
            Producto p2 = tablaProductos.getSelectionModel().getSelectedItem();
            viewModel.updateProducto(p1, p2);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al actualizar el producto");
            alert.setContentText("No se ha seleccionado ningún producto o no se han introducido todos los datos");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaProductos.setItems(viewModel.getProductos());
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        columnaStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
    }
}