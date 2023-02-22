package ui.main;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.pantallas.common.ConstantesPantallas;
import ui.pantallas.common.Pantallas;
import ui.pantallas.principal.PrincipalController;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainFX {

    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            ResourceBundle r = ResourceBundle.getBundle(ConstantesPantallas.I_18_N_TEXTOS_UI);
            fxmlLoader.setResources(r);
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(Pantallas.PRINCIPAL.getRuta()));
            PrincipalController controller = fxmlLoader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(fxmlParent, 900, 500));
            stage.setTitle(ConstantesPantallas.APP_TITLE);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
