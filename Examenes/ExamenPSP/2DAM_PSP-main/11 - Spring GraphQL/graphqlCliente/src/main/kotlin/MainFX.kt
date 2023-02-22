import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import ui.common.Screens
import ui.controllers.PrincipalController


class MainFX : Application() {


    override fun start(primaryStage: Stage?) {
        try {
            val loaderMenu = FXMLLoader(
                javaClass.getResource(Screens.PRINCIPAL.ruta)
            )

            val root: BorderPane? = loaderMenu.load()
            val controller: PrincipalController = loaderMenu.getController()
            controller.setStage(primaryStage)
            val scene = Scene(root)
            primaryStage!!.title = "PersMascs!!!"
            primaryStage.scene = scene
            primaryStage.isResizable = true
            primaryStage.minWidth = 800.0
            primaryStage.minHeight = 1000.0
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
