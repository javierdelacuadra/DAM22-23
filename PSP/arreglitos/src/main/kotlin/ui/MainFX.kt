package ui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import ui.pantallas.common.Pantallas
import ui.pantallas.mainscreen.MainController

class MainFX : Application() {

    override fun start(primaryStage: Stage) {
        try {
            val loaderMenu = FXMLLoader(
                javaClass.getResource(Pantallas.PANTALLAMAIN.ruta)
            )
            val root: BorderPane = loaderMenu.load()
            val scene = Scene(root)
            primaryStage.title = "Cliente GraphQL"
            primaryStage.scene = scene
            primaryStage.show()
            primaryStage.isResizable = false
            val controller = loaderMenu.getController<MainController>()
            controller.setPrimaryStage(primaryStage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(MainFX::class.java, *args)
}
