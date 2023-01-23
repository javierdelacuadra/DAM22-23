package ui.pantallas.common;

import ui.pantallas.common.constantes.ConstantesPantallas;

public enum Pantallas {
    PANTALLAMAIN(ConstantesPantallas.FXML_PANTALLA_MAIN_FXML),
    LISTNEWSPAPERSCREEN(ConstantesPantallas.FXML_LIST_NEWSPAPER_SCREEN_FXML),
    DELETENEWSPAPERSCREEN(ConstantesPantallas.FXML_DELETE_NEWSPAPER_SCREEN_FXML),
    LISTREADERSCREEN(ConstantesPantallas.FXML_LIST_READERS_SCREEN_FXML),
    DELETEREADERSCREEN(ConstantesPantallas.FXML_DELETE_READER_SCREEN_FXML),
    ADDREADERSCREEN(ConstantesPantallas.FXML_ADD_READER_SCREEN_FXML),
    UPDATEREADERSCREEN(ConstantesPantallas.FXML_UPDATE_READER_SCREEN_FXML),
    ADDNEWSPAPERSCREEN(ConstantesPantallas.FXML_ADD_NEWSPAPER_SCREEN_FXML),
    UPDATENEWSPAPERSCREEN(ConstantesPantallas.FXML_UPDATE_NEWSPAPER_SCREEN_FXML),
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
