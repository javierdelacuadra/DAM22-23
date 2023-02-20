package ui.pantallas.common

import ui.pantallas.mainscreen.MainController

open class BasePantallaController {
    private var pantallaMainController: MainController? = null

    fun getPrincipalController(): MainController? {
        return pantallaMainController
    }

    fun setPantallaMainController(pantallaMainController: MainController?) {
        this.pantallaMainController = pantallaMainController
    }

    open fun principalCargado() {}
}
