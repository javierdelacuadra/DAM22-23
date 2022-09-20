package ui.pantallas.pantallaconfig;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
import modelo.Monedero;
import ui.pantallas.common.BasePantallaController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Log4j2
public class PantallaConfigController extends BasePantallaController implements Initializable {

    private final PantallaConfigViewModel viewModel;

    @Inject
    public PantallaConfigController(PantallaConfigViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private MFXButton botonNombre;

    @FXML
    private MFXButton botonDNI;

    @FXML
    private MFXTextField cambioNombreDNI;

    @FXML
    private TableView<Monedero> tablaMonederos;

    @FXML
    private TableColumn<Monedero, String> columnaNombre;

    @FXML
    private TableColumn<Monedero, Double> columnaSaldo;

    @FXML
    private MFXButton addMonedero;

    @FXML
    private MFXButton deleteMonedero;

    @FXML
    private MFXButton updateMonedero;

    @FXML
    private MFXTextField textoNombre;

    @FXML
    private MFXTextField textoSaldo;

    @FXML
    private Label gastoTotal;

    @FXML
    private Label labelNombre;

    @FXML
    private ImageView imagen;

    @FXML
    private MFXComboBox<String> fotoPerfil;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fotoPerfil.getItems().addAll("Perro clásico", "Perro navideño", "Perro chef");
        try (var inputStream = getClass().getResourceAsStream("/images/perro.jpg")) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            imagen.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        tablaMonederos.setItems(viewModel.getMonederos());
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        columnaSaldo.setCellValueFactory(new PropertyValueFactory<>("Dinero"));
    }

    @Override
    public void principalCargado() {
        String dni = this.getPrincipalController().getUsuarioActual().getDni();
        labelNombre.setText(this.getPrincipalController().getUsuarioActual().getNombre());
        viewModel.setMonederos(dni);
        viewModel.getComprasAntiguas(dni);
        gastoTotal.setText(viewModel.getGastoTotal(dni).toString());
    }

    public void cambiarNombre() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cambiar nombre");
        alert.setHeaderText("¿Estás seguro de que quieres cambiar tu nombre?");
        alert.setContentText("Cambiar tu nombre implica la pérdida de tus monederos,\n compras antiguas y gasto total");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String dni = this.getPrincipalController().getUsuarioActual().getDni();
            String nombreViejo = this.getPrincipalController().getUsuarioActual().getNombre();
            String nombreNuevo = cambioNombreDNI.getText();
            viewModel.cambiarNombre(nombreViejo, nombreNuevo, dni);
            this.getPrincipalController().cambiarNombre(cambioNombreDNI.getText());
            labelNombre.setText(this.getPrincipalController().getUsuarioActual().getNombre());
        } else {
            alert.close();
        }
    }

    public void addMonedero() {
        String dni = this.getPrincipalController().getUsuarioActual().getDni();
        if (!textoNombre.getText().isEmpty() || !textoSaldo.getText().isEmpty()) {
            Monedero m = new Monedero(textoNombre.getText(), Double.parseDouble(textoSaldo.getText()));
            viewModel.addMonedero(m, dni);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al añadir monedero");
            alert.setContentText("No se puede añadir un monedero sin nombre o saldo");
            alert.showAndWait();
        }
    }

    public void fotoPerfil() {
        switch (fotoPerfil.getValue()) {
            case "Perro clásico":
                try (var inputStream = getClass().getResourceAsStream("/images/perro.jpg")) {
                    assert inputStream != null;
                    Image logoImage = new Image(inputStream);
                    imagen.setImage(logoImage);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
                break;
            case "Perro navideño":
                try (var inputStream = getClass().getResourceAsStream("/images/perroclaus.png")) {
                    assert inputStream != null;
                    Image logoImage = new Image(inputStream);
                    imagen.setImage(logoImage);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
                break;
            case "Perro chef":
                try (var inputStream = getClass().getResourceAsStream("/images/perrochef.png")) {
                    assert inputStream != null;
                    Image logoImage = new Image(inputStream);
                    imagen.setImage(logoImage);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
                break;
        }
    }
}