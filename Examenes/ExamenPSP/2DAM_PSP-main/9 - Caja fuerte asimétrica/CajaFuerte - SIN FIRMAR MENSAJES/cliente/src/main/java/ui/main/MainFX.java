package ui.main;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.screens.common.ScreenConstants;
import ui.screens.common.Screens;
import ui.screens.principal.PrincipalController;

import java.io.IOException;

public class MainFX {

    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(Screens.PRINCIPAL.getRuta()));
            PrincipalController controller = fxmlLoader.getController();
            controller.setStage(stage);
            // TOMÁS - MODIFICAR MEDIDAS DE LA PANTALLA
            stage.setScene(new Scene(fxmlParent, 1050, 660));
            stage.setTitle(ScreenConstants.NEWSPAPERS_TOMAS);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
