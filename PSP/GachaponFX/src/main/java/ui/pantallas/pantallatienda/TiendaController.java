package ui.pantallas.pantallatienda;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import modelo.Personaje;
import modelo.Usuario;
import servicios.ServiciosTienda;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Log4j2
public class TiendaController extends BasePantallaController implements Initializable {

    private final ServiciosTienda serviciosTienda;

    @Inject
    public TiendaController(ServiciosTienda serviciosTienda) {
        this.serviciosTienda = serviciosTienda;
    }

    @FXML
    private ImageView backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream(ConstantesPantallas.BACK_BUTTON)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            backButton.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void principalCargado() {

    }

    public void volverAtras(MouseEvent mouseEvent) {
        this.getMainController().cargarPantallaBanners();
    }

}