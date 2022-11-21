package ui;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.pantallas.pantallamain.PantallaMainController;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainFX {
    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream("/fxml/PantallaMain.fxml"));
            PantallaMainController controller = fxmlLoader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(fxmlParent));
            stage.setTitle("cliente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}