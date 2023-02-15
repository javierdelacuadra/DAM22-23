package ui.pantallas.common

import ui.pantallas.mainscreen.MainController

class BasePantallaController {
    private var pantallaMainController: MainController? = null

    fun getPrincipalController(): MainController? {
        return pantallaMainController
    }

    fun setPantallaMainController(pantallaMainController: MainController?) {
        this.pantallaMainController = pantallaMainController
    }

    fun principalCargado() {}
}
