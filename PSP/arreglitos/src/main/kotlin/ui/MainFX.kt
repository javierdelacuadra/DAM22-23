package ui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import ui.pantallas.mainscreen.MainController

class MainFX : Application() {

    override fun start(primaryStage: Stage?) {
        try {
            val loaderMenu = FXMLLoader(javaClass.getResource("/fxml/PantallaMain.fxml"))
            val root: BorderPane? = loaderMenu.load()
            val controller: MainController = loaderMenu.getController()
            controller.setStage(primaryStage)
            val scene = Scene(root)
            primaryStage!!.title = "ClienteGraphQL"
            primaryStage.scene = scene
            primaryStage.isResizable = true
            primaryStage.minWidth = 600.0
            primaryStage.minHeight = 800.0
            primaryStage.isMaximized = true
            primaryStage.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(MainFX::class.java, *args)
}
