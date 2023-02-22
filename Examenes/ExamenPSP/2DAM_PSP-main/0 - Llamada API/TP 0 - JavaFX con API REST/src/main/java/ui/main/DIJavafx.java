package ui.main;

import javafx.application.Application;
import javafx.stage.Stage;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.util.AnnotationLiteral;

import java.io.IOException;
import java.util.Properties;

public class DIJavafx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        primaryStage.setResizable(true);
        container.getBeanManager().fireEvent(primaryStage, new AnnotationLiteral<StartupScene>() {});
    }

}
