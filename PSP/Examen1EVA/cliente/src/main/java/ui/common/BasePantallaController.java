package ui.common;

import ui.pantallas.pantallamain.PantallaMainController;

public class BasePantallaController {
    private PantallaMainController PantallaMainController;

    public ui.pantallas.pantallamain.PantallaMainController getPrincipalController() {
        return PantallaMainController;
    }

    public void setPantallaMainController(PantallaMainController PantallaMainController) {
        this.PantallaMainController = PantallaMainController;
    }

    public void principalCargado() {

    }
}
