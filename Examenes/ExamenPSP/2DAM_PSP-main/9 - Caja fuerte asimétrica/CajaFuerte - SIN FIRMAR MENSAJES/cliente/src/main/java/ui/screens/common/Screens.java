package ui.screens.common;

public enum Screens {


    LOGIN(ScreenConstants.FXML_LOGIN_FXML),

    PRINCIPAL(ScreenConstants.FXML_PRINCIPAL_FXML),

    REGISTER(ScreenConstants.FXML_REGISTER_FXML),

    MAIN_FOLDERS(ScreenConstants.FXML_MAIN_FOLDERS_FXML), MAIN_MESSAGES(ScreenConstants.FXML_MAIN_MESSAGES_FXML);

    private final String ruta;

    Screens(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }


}
