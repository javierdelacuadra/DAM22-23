package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import com.sun.javafx.application.*;
import servicios.impl.ServiciosClientesImpl;
import ui.pantallas.pantallalogin.PantallaLoginController;
import ui.pantallas.pantallalogin.PantallaLoginViewModel;
import ui.pantallas.pantallamain.PantallaMainController;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
public class LoginControllerTest extends ApplicationTest {


    private PantallaMainController principalController;
    // = mock(PrincipalController.class);

    private ServiciosClientesImpl serviciosClientes;
    // = mock(LoginUseCase.class);

    private PantallaLoginViewModel viewModel;

    PantallaLoginController controller;

    @BeforeEach
    void setUp() {
        //principalController = mock(PrincipalController.class);
    }

    @Start
    public void start(Stage stage) throws IOException {

        principalController = mock(PantallaMainController.class);
        serviciosClientes = mock(ServiciosClientesImpl.class);
        viewModel = new PantallaLoginViewModel(serviciosClientes);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(param -> new PantallaLoginViewModel(serviciosClientes));
        InputStream s = getClass().getResourceAsStream("/fxml/PantallaLogin.fxml");
        Parent fxmlParent = fxmlLoader.load(s);
        controller = fxmlLoader.getController();
        controller.setPantallaMainController(principalController);

        stage.setScene(new Scene(fxmlParent));
        //stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.show();

    }


    @Test
    void loginAdmin(FxRobot robot) {
        //given
        robot.clickOn("#textFieldNombre");
        robot.write("admin");
        when(serviciosClientes.inicioSesion(argThat(usuario -> usuario.getNombre().equals("admin")))).thenReturn(true);
        //when(loginUseCase.doLogin(any(Usuario.class))).thenReturn(true);

        //when
        robot.clickOn("#botonInicioSesion");

        //FxAssert.verifyThat("#btLogin").textIs("Login");

        //then

        verify(principalController).onLoginHecho(argThat(usuario -> usuario.getNombre().equals("admin")));
    }

    @Test
    void loginFallo(FxRobot robot) {
        //given
        robot.clickOn("#textFieldNombre");
        robot.write("noadmin");
        when(serviciosClientes.inicioSesion(argThat(usuario -> usuario.getNombre().equals("admin")))).thenReturn(false);
        //when(loginUseCase.doLogin(any(Usuario.class))).thenReturn(true);


        //when
        robot.clickOn(".button");

        //then
        assertAll(
                () -> verify(principalController).sacarAlertError("Escribe un nombre y un DNI para iniciar sesión"));
//        Node dialogPane = robot.lookup(".dialog-pane").query();
//        assertNotNull(robot.from(dialogPane).lookup((Text t) -> t.getText().startsWith("usuario o pass no valido")).query());
        robot.sleep(1000);

    }


}
