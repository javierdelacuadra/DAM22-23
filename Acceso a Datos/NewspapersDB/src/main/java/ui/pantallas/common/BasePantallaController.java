package ui.pantallas.common;

import ui.pantallas.pantallamain.PantallaMainController;

public class BasePantallaController {
    private PantallaMainController PantallaMainController;

    public PantallaMainController getPrincipalController() {
        return PantallaMainController;
    }

    public void setPantallaMainController(PantallaMainController PantallaMainController) {
        this.PantallaMainController = PantallaMainController;
    }

    public void principalCargado() {

    }
}
