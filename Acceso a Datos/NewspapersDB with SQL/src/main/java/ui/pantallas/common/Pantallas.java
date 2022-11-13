package ui.pantallas.common;

public enum Pantallas {
    PANTALLAMAIN("/fxml/PantallaMain.fxml"),
    PANTALLALOGIN("/fxml/PantallaLogin.fxml"),
    LISTNEWSPAPERSCREEN("/fxml/ListNewspaperScreen.fxml"),
    DELETENEWSPAPERSCREEN("/fxml/DeleteNewspaperScreen.fxml"),
    LISTARTICLESCREEN("/fxml/ListArticleScreen.fxml"),
    ADDARTICLESCREEN("/fxml/AddArticleScreen.fxml"),
    LISTREADERSCREEN("/fxml/ListReadersScreen.fxml"),
    DELETEREADERSCREEN("/fxml/DeleteReaderScreen.fxml"),
    ADDREADERSCREEN("/fxml/AddReaderScreen.fxml"),
    UPDATEREADERSCREEN("/fxml/UpdateReaderScreen.fxml"),
    ADDREADARTICLESCREEN("/fxml/AddReadArticleScreen.fxml"),
    ADDSUBSCRIPTIONSCREEN("/fxml/AddSubscriptionScreen.fxml"),
    ADDNEWSPAPERSCREEN("/fxml/AddNewspaperScreen.fxml"),
    UPDATENEWSPAPERSCREEN("/fxml/UpdateNewspaperScreen.fxml"),
    QUERIESSCREEN("/fxml/QueriesScreen.fxml");

    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
