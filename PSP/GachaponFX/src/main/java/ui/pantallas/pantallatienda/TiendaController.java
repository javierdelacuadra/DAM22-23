package ui.pantallas.pantallatienda;

import io.github.palexdev.materialfx.controls.MFXTextField;
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
import modelo.ResponseUser;
import modelo.Usuario;
import servicios.ServiciosFarmeo;
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

    private final ServiciosFarmeo serviciosFarmeo;

    @Inject
    public TiendaController(ServiciosFarmeo serviciosFarmeo) {
        this.serviciosFarmeo = serviciosFarmeo;
    }

    @FXML
    private ImageView backButton;

    @FXML
    private ImageView starIcon;

    @FXML
    private ImageView diamondIcon;

    @FXML
    private ImageView scoinsIcon;

    @FXML
    private ImageView ucoinsIcon;

    @FXML
    private ImageView demonIcon;

    @FXML
    private ImageView cpIcon;

    @FXML
    private Label starLabel;

    @FXML
    private Label diamondLabel;

    @FXML
    private Label scoinsLabel;

    @FXML
    private Label ucoinsLabel;

    @FXML
    private Label demonLabel;

    @FXML
    private Label cpLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private MFXTextField background;

    public void loadImage(String path, ImageView imageView) {
        try (var inputStream = getClass().getResourceAsStream(path)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            imageView.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadImage(ConstantesPantallas.BACK_BUTTON, backButton);
        loadImage(ConstantesPantallas.STAR_ICON, starIcon);
        loadImage(ConstantesPantallas.DIAMOND_ICON, diamondIcon);
        loadImage(ConstantesPantallas.SCOINS_ICON, scoinsIcon);
        loadImage(ConstantesPantallas.UCOINS_ICON, ucoinsIcon);
        loadImage(ConstantesPantallas.DEMON_ICON, demonIcon);
        loadImage(ConstantesPantallas.CP_ICON, cpIcon);
        background.setDisable(true);
    }

    @Override
    public void principalCargado() {
        ResponseUser responseUser = getMainController().getResponseUser();
        usernameLabel.setText(responseUser.getUsername());
        starLabel.setText(responseUser.getStars() + "");
        diamondLabel.setText(responseUser.getDiamonds() + "");
        scoinsLabel.setText(responseUser.getCoins() + "");
        ucoinsLabel.setText(responseUser.getUserCoins() + "");
        demonLabel.setText(responseUser.getDemons() + "");
        cpLabel.setText(responseUser.getCp() + "");
    }

    public void volverAtras(MouseEvent mouseEvent) {
        this.getMainController().cargarPantallaBanners();
    }

}