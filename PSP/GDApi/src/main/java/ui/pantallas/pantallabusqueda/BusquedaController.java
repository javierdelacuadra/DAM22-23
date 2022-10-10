package ui.pantallas.pantallabusqueda;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
import modelo.ResponseLevelsItem;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import servicios.ServiciosNiveles;
import servicios.ServiciosUsers;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class BusquedaController extends BasePantallaController implements Initializable {

    private final ServiciosNiveles serviciosNiveles;
    private final ServiciosUsers serviciosUsers;

    @Inject
    public BusquedaController(ServiciosNiveles serviciosNiveles, ServiciosUsers serviciosUsers) {
        this.serviciosNiveles = serviciosNiveles;
        this.serviciosUsers = serviciosUsers;
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
    private ImageView infoButton;

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
        loadImage(ConstantesPantallas.INFO_BUTTON, infoButton);
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

    public void mostrarNiveles() {
        String text = searchBox.getText();
        if (text.isBlank()) {
            text = ConstantesPantallas.ANY;
        }
        String diff = parseDifficulty();

        boolean doSearch;
        if (text.equals(ConstantesPantallas.ANY) && diff.equals(ConstantesPantallas.NADA)) {
            doSearch = true;
        } else doSearch = text.length() >= 3 || !diff.equals(ConstantesPantallas.NADA);
        if (doSearch) {
            getMainController().root.setCursor(Cursor.WAIT);
            String finalText = text;
            var task = new Task<Either<String, List<ResponseLevelsItem>>>() {
                @Override
                protected Either<String, List<ResponseLevelsItem>> call() {
                    return serviciosNiveles.getNiveles(finalText, diff);
                }
            };
            task.setOnSucceeded(workerStateEvent -> {
                //workerStateEvent.getSource().valueProperty().get()
                getMainController().root.setCursor(Cursor.DEFAULT);
                var result = task.getValue();
                result.peek(niveles -> {
                    this.getMainController().setResponseLevels(niveles);
                    this.getMainController().cargarPantallaNiveles();
                }).peekLeft(error -> getMainController().crearAlertError(error));
            });
            task.setOnFailed(workerStateEvent -> {
                //workerStateEvent.getSource().getException().getMessage()
                getMainController().crearAlertError(task.getException().getMessage());
                getMainController().root.setCursor(Cursor.DEFAULT);
            });
            new Thread(task).start();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ConstantesPantallas.ERROR);
            alert.setHeaderText(ConstantesPantallas.ERROR);
            alert.setContentText(ConstantesPantallas.EL_TEXTO_DE_BUSQUEDA_DEBE_TENER_AL_MENOS_3_CARACTERES_CUANDO_NO_SE_SELECCIONA_NINGUN_FILTRO);
            alert.showAndWait();
        }
    }

    public String parseDifficulty() {
        StringBuilder difficultyString = new StringBuilder();
        String code = createCode();
        if (!code.isEmpty()) {
            for (int i = 0; i < code.length(); i++) {
                if (code.charAt(i) == ConstantesPantallas.CEROCHAR) {
                    difficultyString.append(ConstantesPantallas.MENOS_DOS);
                } else if (code.charAt(i) == ConstantesPantallas.SEISCHAR) {
                    difficultyString.append(ConstantesPantallas.MENOS_TRES);
                } else {
                    difficultyString.append(code.charAt(i));
                }
                if (i != code.length() - 1) {
                    difficultyString.append(ConstantesPantallas.COMA);
                }
            }
        }
        return difficultyString.toString();
    }

    public String createCode() {
        StringBuilder code = new StringBuilder();
        if (autoButton.isSelected()) {
            code.append(ConstantesPantallas.CERO);
        }
        if (easyButton.isSelected()) {
            code.append(ConstantesPantallas.UNO);
        }
        if (normalButton.isSelected()) {
            code.append(ConstantesPantallas.DOS);
        }
        if (hardButton.isSelected()) {
            code.append(ConstantesPantallas.TRES);
        }
        if (harderButton.isSelected()) {
            code.append(ConstantesPantallas.CUATRO);
        }
        if (insaneButton.isSelected()) {
            code.append(ConstantesPantallas.CINCO);
        }
        if (demonButton.isSelected()) {
            code.append(ConstantesPantallas.SEIS);
        }
        return code.toString();
    }

    public void cargarUsuario() {
        String text = searchBox.getText();

        Single.fromCallable(() -> serviciosUsers.getUser(text))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> getMainController().root.setCursor(Cursor.DEFAULT))
                .subscribe(result ->
                                result.peek(user -> {
                                            this.getMainController().setResponseUser(user);
                                            this.getMainController().cargarPantallaUsers();
                                        })
                                        .peekLeft(error -> getMainController().crearAlertError(error)),
                        throwable -> getMainController().crearAlertError(throwable.getMessage()));
        getMainController().root.setCursor(Cursor.WAIT);
    }

    public void autoFilter() {
        if (autoButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_AUTO_ON, filterAuto);
        } else {
            loadImage(ConstantesPantallas.FILTER_AUTO_OFF, filterAuto);
        }
    }

    public void easyFilter() {
        if (easyButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_EASY_ON, filterEasy);
        } else {
            loadImage(ConstantesPantallas.FILTER_EASY_OFF, filterEasy);
        }
    }


    public void normalFilter() {
        if (normalButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_NORMAL_ON, filterNormal);
        } else {
            loadImage(ConstantesPantallas.FILTER_NORMAL_OFF, filterNormal);
        }
    }

    public void hardFilter() {
        if (hardButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_HARD_ON, filterHard);
        } else {
            loadImage(ConstantesPantallas.FILTER_HARD_OFF, filterHard);
        }
    }

    public void harderFilter() {
        if (harderButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_HARDER_ON, filterHarder);
        } else {
            loadImage(ConstantesPantallas.FILTER_HARDER_OFF, filterHarder);
        }
    }

    public void insaneFilter() {
        if (insaneButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_INSANE_ON, filterInsane);
        } else {
            loadImage(ConstantesPantallas.FILTER_INSANE_OFF, filterInsane);
        }
    }

    public void demonFilter() {
        if (demonButton.isSelected()) {
            loadImage(ConstantesPantallas.FILTER_DEMON_ON, filterDemon);
        } else {
            loadImage(ConstantesPantallas.FILTER_DEMON_OFF, filterDemon);
        }
    }

    public void ayuda() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ConstantesPantallas.AYUDA);
        alert.setHeaderText(ConstantesPantallas.AYUDA);
        alert.setContentText(ConstantesPantallas.TEXTO_AYUDA);
        alert.showAndWait();
    }

    public void volverAtras() {
        this.getMainController().cargarPantallaInicio();
    }
}