package org.cliente.ui.getAllJavaFX.main;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.cliente.ui.getAllJavaFX.common.ConstantesPantallas;
import org.cliente.ui.getAllJavaFX.common.Pantallas;
import org.cliente.ui.getAllJavaFX.principal.PrincipalController;

import java.io.IOException;

public class MainFX {

    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
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
