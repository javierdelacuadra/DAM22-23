package ui.screens.common;

public enum Screens {

    ADDART(ScreenConstants.FXML_NEWSPAPERS_ADD_ARTICLES_FXML),

    ADDNEWS(ScreenConstants.FXML_NEWSPAPERS_ADD_NEWS_FXML),
    ADDREADART(ScreenConstants.FXML_NEWSPAPERS_ADD_READ_ARTICLE_FXML),
    ADDREADER(ScreenConstants.FXML_READERS_ADD_READER_FXML),

    CHANGESUBS(ScreenConstants.FXML_CHANGESUBS_FXML),

    DELNEWS(ScreenConstants.FXML_NEWSPAPERS_DEL_NEWS_FXML),

    DELREADERS(ScreenConstants.FXML_READERS_DELREADERS_NEWS_FXML),

    LISTART(ScreenConstants.FXML_NEWSPAPERS_LIST_ARTICLES_FXML),

    LISTNEWS(ScreenConstants.FXML_NEWSPAPERS_LIST_NEWS_FXML),

    LISTREADERS(ScreenConstants.FXML_READERS_LISTREADERS_NEWS_FXML),

    LOGIN(ScreenConstants.FXML_LOGIN_FXML),
    PRINCIPAL(ScreenConstants.FXML_PRINCIPAL_FXML), UPDNEWS(ScreenConstants.FXML_NEWSPAPERS_UPD_NEWS_FXML), UPDREADER(ScreenConstants.FXML_READERS_UPDATE_READER_FXML);

    private String ruta;

    Screens(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }


}
