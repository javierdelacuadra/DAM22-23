package ui.pantallas.pantallabanners;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import servicios.ServiciosBanner;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class BannersController extends BasePantallaController implements Initializable {

    private final ServiciosBanner serviciosBanner;

    @Inject
    public BannersController(ServiciosBanner serviciosBanner) {
        this.serviciosBanner = serviciosBanner;
    }

    @FXML
    private ImageView searchButton;

    @FXML
    private ImageView userSearchButton;

    @FXML
    private MFXTextField difficulty;

    @FXML
    private ImageView filterAuto;

    @FXML
    private ImageView filterEasy;

    @FXML
    private ImageView filterNormal;

    @FXML
    private ImageView filterHard;

    @FXML
    private ImageView filterHarder;

    @FXML
    private ImageView filterInsane;

    @FXML
    private ImageView filterDemon;

    @FXML
    private ImageView plusButton;

    @FXML
    private ImageView backButton;

    @FXML
    private MFXToggleButton autoButton;

    @FXML
    private MFXToggleButton easyButton;

    @FXML
    private MFXToggleButton normalButton;

    @FXML
    private MFXToggleButton hardButton;

    @FXML
    private MFXToggleButton harderButton;

    @FXML
    private MFXToggleButton insaneButton;

    @FXML
    private MFXToggleButton demonButton;

    @FXML
    private MFXCheckbox ratedCheckBox;

    @FXML
    private MFXCheckbox featuredCheckBox;

    @FXML
    private MFXCheckbox epicCheckBox;

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
        loadImage(ConstantesPantallas.SEARCH_BUTTON, searchButton);
        loadImage(ConstantesPantallas.USER_SEARCH_BUTTON, userSearchButton);
        loadImage(ConstantesPantallas.FILTER_AUTO_OFF, filterAuto);
        loadImage(ConstantesPantallas.FILTER_EASY_OFF, filterEasy);
        loadImage(ConstantesPantallas.FILTER_NORMAL_OFF, filterNormal);
        loadImage(ConstantesPantallas.FILTER_HARD_OFF, filterHard);
        loadImage(ConstantesPantallas.FILTER_HARDER_OFF, filterHarder);
        loadImage(ConstantesPantallas.FILTER_INSANE_OFF, filterInsane);
        loadImage(ConstantesPantallas.FILTER_DEMON_OFF, filterDemon);
        loadImage(ConstantesPantallas.PLUS_BUTTON, plusButton);
        loadImage(ConstantesPantallas.BACK_BUTTON, backButton);
        difficulty.setDisable(true);
        autoButton.setOpacity(0);
        easyButton.setOpacity(0);
        normalButton.setOpacity(0);
        hardButton.setOpacity(0);
        harderButton.setOpacity(0);
        insaneButton.setOpacity(0);
        demonButton.setOpacity(0);
    }

    @Override
    public void principalCargado() {

    }

    public void volverAtras(MouseEvent mouseEvent) {
        this.getMainController().saveUsuario();
        this.getMainController().cargarInicio();
    }

    public void filtroEpic(ActionEvent actionEvent) {
        if (epicCheckBox.isSelected()) {
            featuredCheckBox.setSelected(true);
            ratedCheckBox.setSelected(true);
        } else {
            featuredCheckBox.setSelected(false);
            ratedCheckBox.setSelected(false);
        }
    }

    public void filtroFeatured(ActionEvent actionEvent) {
        if (!featuredCheckBox.isSelected() && epicCheckBox.isSelected()) {
            epicCheckBox.setSelected(false);
        } else if (featuredCheckBox.isSelected() && !ratedCheckBox.isSelected()) {
            ratedCheckBox.setSelected(true);
        }
    }

    public void filtroRated(ActionEvent actionEvent) {
        if (!ratedCheckBox.isSelected() && (epicCheckBox.isSelected() || featuredCheckBox.isSelected())) {
            epicCheckBox.setSelected(false);
            featuredCheckBox.setSelected(false);
        }
    }

    public void cargarResultado(MouseEvent mouseEvent) {
        this.getMainController().cargarFarmeo();
    }

    public void cargarUsuario(MouseEvent mouseEvent) {
        this.getMainController().cargarFarmeo();
    }

    public void autoFilter(MouseEvent mouseEvent) {
        if (autoButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_AUTO_ON, filterAuto);
        } else {
            loadImage(ConstantesPantallas.FILTER_AUTO_OFF, filterAuto);
        }
    }

    public void easyFilter(MouseEvent mouseEvent) {
        if (easyButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_EASY_ON, filterEasy);
        } else {
            loadImage(ConstantesPantallas.FILTER_EASY_OFF, filterEasy);
        }
    }


    public void normalFilter(MouseEvent mouseEvent) {
        if (normalButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_NORMAL_ON, filterNormal);
        } else {
            loadImage(ConstantesPantallas.FILTER_NORMAL_OFF, filterNormal);
        }
    }

    public void hardFilter(MouseEvent mouseEvent) {
        if (hardButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_HARD_ON, filterHard);
        } else {
            loadImage(ConstantesPantallas.FILTER_HARD_OFF, filterHard);
        }
    }

    public void harderFilter(MouseEvent mouseEvent) {
        if (harderButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_HARDER_ON, filterHarder);
        } else {
            loadImage(ConstantesPantallas.FILTER_HARDER_OFF, filterHarder);
        }
    }

    public void insaneFilter(MouseEvent mouseEvent) {
        if (insaneButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_INSANE_ON, filterInsane);
        } else {
            loadImage(ConstantesPantallas.FILTER_INSANE_OFF, filterInsane);
        }
    }

    public void demonFilter(MouseEvent mouseEvent) {
        if (demonButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_DEMON_ON, filterDemon);
        } else {
            loadImage(ConstantesPantallas.FILTER_DEMON_OFF, filterDemon);
        }
    }
}