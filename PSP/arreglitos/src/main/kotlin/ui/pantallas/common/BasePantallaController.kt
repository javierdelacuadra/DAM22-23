package ui.pantallas.common

import ui.pantallas.mainscreen.MainController

open class BasePantallaController {

    private var principalController: MainController? = null

    fun getPrincipalController(): MainController? {
        return principalController
    }

    fun setPrincipalController(principalController: MainController?) {
        this.principalController = principalController
    }

    open fun principalCargado() {
        // principalCargado() is called when the screen is loaded
    }
}
