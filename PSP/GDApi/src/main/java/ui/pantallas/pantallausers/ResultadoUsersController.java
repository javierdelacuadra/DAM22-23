package ui.pantallas.pantallausers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
import modelo.ResponseUser;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class ResultadoUsersController extends BasePantallaController implements Initializable {

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
        starLabel.setText(responseUser.getStars() + ConstantesPantallas.NADA);
        diamondLabel.setText(responseUser.getDiamonds() + ConstantesPantallas.NADA);
        scoinsLabel.setText(responseUser.getCoins() + ConstantesPantallas.NADA);
        ucoinsLabel.setText(responseUser.getUserCoins() + ConstantesPantallas.NADA);
        demonLabel.setText(responseUser.getDemons() + ConstantesPantallas.NADA);
        cpLabel.setText(responseUser.getCp() + ConstantesPantallas.NADA);
    }

    public void volverAtras() {
        this.getMainController().cargarPantallaBusqueda();
    }

}