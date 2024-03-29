package ui;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.common.ConstantesUI;
import ui.pantallas.pantallamain.PantallaMainController;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainFX {
    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            ResourceBundle r = ResourceBundle.getBundle(ConstantesUI.BUNDLE);
            fxmlLoader.setResources(r);
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(ConstantesUI.FXML_PANTALLA_MAIN_FXML));
            PantallaMainController controller = fxmlLoader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(fxmlParent));
            //stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setTitle(r.getString(ConstantesUI.TITULO));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}