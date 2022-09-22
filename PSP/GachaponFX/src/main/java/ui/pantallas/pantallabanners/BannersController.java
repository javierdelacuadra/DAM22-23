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
import modelo.ResponseLevels;
import modelo.ResponseLevelsItem;
import servicios.ServiciosBusqueda;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class BannersController extends BasePantallaController implements Initializable {

    private final ServiciosBusqueda serviciosBusqueda;

    @Inject
    public BannersController(ServiciosBusqueda serviciosBusqueda) {
        this.serviciosBusqueda = serviciosBusqueda;
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

    @FXML
    private MFXTextField searchBox;

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

    public void cargarResultado(MouseEvent mouseEvent) throws IOException {
        String text = searchBox.getText();
        if (text.isBlank()) {
            text = "*";
        }
        StringBuilder difficulty = new StringBuilder();
        StringBuilder code = new StringBuilder();
        if (autoButton.isSelected()) {
            code.append("0");
        }
        if (easyButton.isSelected()) {
            code.append("1");
        }
        if (normalButton.isSelected()) {
            code.append("2");
        }
        if (hardButton.isSelected()) {
            code.append("3");
        }
        if (harderButton.isSelected()) {
            code.append("4");
        }
        if (insaneButton.isSelected()) {
            code.append("5");
        }
        if (demonButton.isSelected()) {
            code.append("6");
        }
        if (!code.toString().isEmpty()){
            for (int i = 0; i < code.toString().length(); i++) {
                if (code.toString().charAt(i) == '0') {
                    difficulty.append("-2");
                } else if (code.toString().charAt(i) == '6') {
                    difficulty.append("-3");
                } else {
                    difficulty.append(code.toString().charAt(i));
                }
                if (i != code.toString().length() - 1) {
                    difficulty.append(",");
                }
            }
        }
        boolean rated = ratedCheckBox.isSelected();
        boolean featured = featuredCheckBox.isSelected();
        boolean epic = epicCheckBox.isSelected();
        List<ResponseLevelsItem> lista = serviciosBusqueda.getNiveles(text, String.valueOf(difficulty), rated, featured, epic);
        ResponseLevels responseLevels = this.getMainController().getResponseLevels();
        responseLevels.setResponseLevelsList(lista);
        this.getMainController().setResponseLevels(responseLevels);
        this.getMainController().cargarFarmeo();
    }

    public void cargarUsuario(MouseEvent mouseEvent) {
        this.getMainController().cargarTienda();
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