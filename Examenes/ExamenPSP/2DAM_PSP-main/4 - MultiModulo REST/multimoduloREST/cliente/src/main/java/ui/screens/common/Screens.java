package ui.screens.common;

public enum Screens {


    LISTNEWS(ScreenConstants.FXML_NEWSPAPERS_LIST_NEWS_FXML),

    LISTREADERS(ScreenConstants.FXML_READERS_LISTREADERS_NEWS_FXML),

    PRINCIPAL(ScreenConstants.FXML_PRINCIPAL_FXML),

    UPDNEWS(ScreenConstants.FXML_NEWSPAPERS_UPD_NEWS_FXML),

    UPDREADER(ScreenConstants.FXML_READERS_UPDATE_READER_FXML);

    private final String ruta;

    Screens(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }


}
