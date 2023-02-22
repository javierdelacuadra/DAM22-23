package ui.pantallas.common;

import ui.pantallas.principal.PrincipalController;


public class BasePantallaController {

    private PrincipalController principalController;

    public PrincipalController getPrincipalController() {
        return principalController;
    }

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void principalCargado() {
        //metodo que utilizaran los controllers para inicializar cosas con el principal controller
    }
}
