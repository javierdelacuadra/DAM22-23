package ui.pantallas.pantallacliente;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
import modelo.LineaCompra;
import modelo.Monedero;
import modelo.Producto;
import ui.pantallas.common.BasePantallaController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class PantallaClienteController extends BasePantallaController implements Initializable {

    private final PantallaClienteViewModel viewModel;

    @Inject
    private PantallaClienteController(PantallaClienteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> columnaProductosNombre;

    @FXML
    private TableColumn<Producto, Double> columnaProductosPrecio;

    @FXML
    private TableColumn<Producto, Integer> columnaProductosStock;

    @FXML
    private TableView<Producto> tablaCarrito;

    @FXML
    private TableColumn<Producto, String> columnaCarritoNombre;

    @FXML
    private TableColumn<Producto, Double> columnaCarritoPrecio;

    @FXML
    private TableColumn<Producto, Integer> columnaCarritoCantidad;

    @FXML
    private TableView<Monedero> tablaMonederos;

    @FXML
    private TableColumn<Monedero, String> columnaNombreMonedero;

    @FXML
    private TableColumn<Monedero, Double> columnaSaldoMonedero;

    @FXML
    private ImageView anuncio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaProductos.setItems(viewModel.getProductos());
        tablaMonederos.setItems(viewModel.getMonederos());
        tablaCarrito.setItems(viewModel.getCarrito());
        columnaProductosNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        columnaProductosPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        columnaProductosStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        columnaCarritoNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        columnaCarritoPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        columnaCarritoCantidad.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        columnaNombreMonedero.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        columnaSaldoMonedero.setCellValueFactory(new PropertyValueFactory<>("Dinero"));
        try (var inputStream = getClass().getResourceAsStream("/anuncios/anunciomessi.jpg")) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            anuncio.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void principalCargado() {
        String dni = this.getPrincipalController().getUsuarioActual().getDni();
        viewModel.setMonederos(dni);
        viewModel.setCarrito(dni);
    }

    public void addProductoCarrito() {
        if (tablaProductos.getSelectionModel().getSelectedItem() != null) {
            if (viewModel.addProducto(tablaProductos.getSelectionModel().getSelectedItem(), this.getPrincipalController().getUsuarioActual().getDni())) {
                tablaProductos.refresh();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No queda stock del producto");
                alert.setContentText("Selecciona otro producto para añadir al carrito");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ningún producto");
            alert.setContentText("Por favor, seleccione un producto");
            alert.showAndWait();
        }
    }

    public void eliminarProductoCarrito(ActionEvent actionEvent) {
        if (tablaCarrito.getSelectionModel().getSelectedItem() != null) {
            viewModel.removeProducto(tablaCarrito.getSelectionModel().getSelectedItem(), this.getPrincipalController().getUsuarioActual().getDni());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ningún producto");
            alert.setContentText("Por favor, seleccione un producto");
            alert.showAndWait();
        }

    }

    public void comprar(ActionEvent actionEvent) {
        if (tablaMonederos.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No has seleccionado ningún monedero");
            alert.setContentText("No has seleccionado ningún monedero");
            alert.showAndWait();
        } else if (tablaCarrito != null) {
            if (viewModel.pagar(tablaMonederos.getSelectionModel().getSelectedItem().getNombre(), this.getPrincipalController().getUsuarioActual().getDni())) {
                tablaMonederos.refresh();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Milagro");
                alert.setHeaderText("Gracias por tu compra");
                alert.setContentText("Gracias por tu compra");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se ha podido comprar");
                alert.setContentText("No hay productos en el carrito o no hay monedero seleccionado");
                alert.showAndWait();
            }
        }
    }
}