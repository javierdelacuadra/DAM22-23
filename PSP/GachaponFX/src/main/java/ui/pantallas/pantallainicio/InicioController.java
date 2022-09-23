package ui.pantallas.pantallainicio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class InicioController extends BasePantallaController implements Initializable {

    @FXML
    private ImageView gdlogo;

    @FXML
    private ImageView playbutton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream(ConstantesPantallas.LOGO_ICON)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            gdlogo.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        try (var inputStream = getClass().getResourceAsStream(ConstantesPantallas.PLAY_BUTTON)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            playbutton.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void principalCargado() {

    }

    public void cargarPantallaBanners() {
        this.getMainController().cargarPantallaBanners();
    }

    public void cargarPantallaFarmeo(ActionEvent actionEvent) {
        this.getMainController().cargarFarmeo();
    }

    public void cargarPantallaTienda(ActionEvent actionEvent) {
        this.getMainController().cargarTienda();
    }

    public void cargarPantallaInventario(ActionEvent actionEvent) {
        this.getMainController().cargarInventario(actionEvent);
    }
}