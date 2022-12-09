package ui.pantallas.pantallalogin;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
import model.Reader;
import model.ReaderLogin;
import ui.common.ConstantesUI;
import ui.pantallas.common.BasePantallaController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Log4j2
public class PantallaLoginController extends BasePantallaController implements Initializable {

    private final LoginViewModel viewModel;

    @Inject
    public PantallaLoginController(LoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private MFXTextField textfieldNombre;

    @FXML
    private MFXTextField textfieldPassword;

    @FXML
    private MFXTextField textfieldEmail;

    @FXML
    private MFXButton menuLoginButton;

    @FXML
    private MFXButton menuRegistroButton;

    @FXML
    private MFXButton menuOpcionesButton;

    @FXML
    private MFXButton botonInicioSesion;

    @FXML
    private MFXButton botonRegistro;

    @FXML
    private MFXButton botonRecuperarPassword;

    @FXML
    private MFXButton botonMandarEmail;

    @FXML
    private ImageView logoSQL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream(ConstantesUI.MY_SQL_LOGO_PATH)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            logoSQL.setImage(logoImage);
        } catch (IOException e) {
            log.error("Error al cargar el logo de MySQL", e);
        }

        viewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.mensaje != null) {
                Platform.runLater(() -> {
                    getPrincipalController().createAlert(newState.mensaje);
                });
            }
            if (newState.readerLogin != null) {
                Platform.runLater(() -> {
                    getPrincipalController().setReader(new Reader(newState.readerLogin.getId_reader(), newState.readerLogin.getUsername(), LocalDate.now()));
                    getPrincipalController().onLoginHecho(newState.readerLogin.getId_reader() <= 0);
                });
            }
        });
        textfieldNombre.setVisible(false);
        textfieldPassword.setVisible(false);
        textfieldEmail.setVisible(false);
        botonInicioSesion.setVisible(false);
        botonRegistro.setVisible(false);
        botonRecuperarPassword.setVisible(false);
        botonMandarEmail.setVisible(false);
    }

    @FXML
    public void inicioSesion() {
        String nombre = textfieldNombre.getText();
        String password = textfieldPassword.getText();

        if (nombre == null || nombre.isEmpty() || password == null || password.isEmpty()) {
            this.getPrincipalController().createAlert(ConstantesUI.PLEASE_CHECK_YOUR_CREDENTIALS);
        } else {
            ReaderLogin readerLogin = new ReaderLogin(nombre, password, "");
            viewModel.login(readerLogin);
        }
    }

    public void registro() {
        String nombre = textfieldNombre.getText();
        String password = textfieldPassword.getText();
        String email = textfieldEmail.getText();

        if (nombre == null || nombre.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            this.getPrincipalController().createAlert(ConstantesUI.PLEASE_CHECK_YOUR_CREDENTIALS);
        } else {
            ReaderLogin readerLogin = new ReaderLogin(nombre, password, email, 0, "", 0, LocalDateTime.now());
            Integer id = viewModel.register(readerLogin);
            if (id == -2) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ConstantesUI.ERROR);
                alert.setHeaderText(ConstantesUI.COULD_NOT_REGISTER);
                alert.setContentText(ConstantesUI.PLEASE_CHECK_YOUR_CREDENTIALS);
                alert.showAndWait();
            } else if (id == -3) {
                this.getPrincipalController().createAlert(ConstantesUI.THERE_WAS_AN_ERROR_TRYING_TO_REGISTER);
            } else {
                this.getPrincipalController().setReader(viewModel.getReader(id));
                this.getPrincipalController().onLoginHecho(id <= 0);
            }
        }
    }

    public void recuperarPassword() {
        String email = textfieldEmail.getText();

        if (email == null || email.isEmpty()) {
            this.getPrincipalController().createAlert(ConstantesUI.PLEASE_CHECK_YOUR_CREDENTIALS);
        } else {
            viewModel.recoverPassword(email);
        }
    }

    public void mandarEmail() {
        String email = textfieldEmail.getText();

        if (email == null || email.isEmpty()) {
            this.getPrincipalController().createAlert(ConstantesUI.PLEASE_CHECK_YOUR_CREDENTIALS);
        } else {
            viewModel.sendEmail(email);
        }
    }

    public void menuLogin() {
        menuLoginButton.setDisable(true);
        menuRegistroButton.setDisable(false);
        menuOpcionesButton.setDisable(false);
        textfieldNombre.setVisible(true);
        textfieldPassword.setVisible(true);
        textfieldEmail.setVisible(false);
        botonInicioSesion.setVisible(true);
        botonRegistro.setVisible(false);
        botonRecuperarPassword.setVisible(false);
        botonMandarEmail.setVisible(false);
    }

    public void menuRegistro() {
        menuLoginButton.setDisable(false);
        menuRegistroButton.setDisable(true);
        menuOpcionesButton.setDisable(false);
        textfieldNombre.setVisible(true);
        textfieldPassword.setVisible(true);
        textfieldEmail.setVisible(true);
        botonInicioSesion.setVisible(false);
        botonRegistro.setVisible(true);
        botonRecuperarPassword.setVisible(false);
        botonMandarEmail.setVisible(false);
    }

    public void menuOpciones() {
        menuLoginButton.setDisable(false);
        menuRegistroButton.setDisable(false);
        menuOpcionesButton.setDisable(true);
        textfieldNombre.setVisible(false);
        textfieldPassword.setVisible(false);
        textfieldEmail.setVisible(true);
        botonInicioSesion.setVisible(false);
        botonRegistro.setVisible(false);
        botonRecuperarPassword.setVisible(true);
        botonMandarEmail.setVisible(true);
    }
}