package ui.pantallas.pantallafarmeo;

import io.github.palexdev.materialfx.controls.MFXButton;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import modelo.Usuario;
import servicios.ServiciosFarmeo;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class FarmeoController extends BasePantallaController implements Initializable {

    private final ServiciosFarmeo serviciosFarmeo;

    @Inject
    public FarmeoController(ServiciosFarmeo serviciosFarmeo) {
        this.serviciosFarmeo = serviciosFarmeo;
    }

    @FXML
    private MFXButton extraInfo;

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
        extraInfo.setText("Información\n Adicional");
    }

    @Override
    public void principalCargado() {

    }

    public void volverAtras(MouseEvent mouseEvent) {
        this.getMainController().cargarPantallaBanners();
    }
}