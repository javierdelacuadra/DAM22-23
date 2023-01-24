package ui.pantallas.common;

import ui.pantallas.common.constantes.ConstantesPantallas;

public enum Pantallas {
    PANTALLAMAIN(ConstantesPantallas.FXML_PANTALLA_MAIN_FXML),
    ADDREADERSCREEN(ConstantesPantallas.FXML_ADD_READER_SCREEN_FXML),
    LOGINSCREEN(ConstantesPantallas.FXML_PANTALLA_LOGIN_FXML),
    LISTCARPETASSCREEN(ConstantesPantallas.FXML_LIST_CARPETAS_SCREEN_FXML),
    ADDCARPETASSCREEN(ConstantesPantallas.FXML_ADD_CARPETA_SCREEN_FXML);

    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
