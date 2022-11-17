package ui.pantallas.common;

public enum Pantallas {
    PANTALLAMAIN("/fxml/PantallaMain.fxml"),
    LISTNEWSPAPERSCREEN("/fxml/ListNewspaperScreen.fxml"),
    DELETENEWSPAPERSCREEN("/fxml/DeleteNewspaperScreen.fxml"),
    LISTREADERSCREEN("/fxml/ListReadersScreen.fxml"),
    DELETEREADERSCREEN("/fxml/DeleteReaderScreen.fxml"),
    ADDREADERSCREEN("/fxml/AddReaderScreen.fxml"),
    UPDATEREADERSCREEN("/fxml/UpdateReaderScreen.fxml"),
    ADDNEWSPAPERSCREEN("/fxml/AddNewspaperScreen.fxml"),
    UPDATENEWSPAPERSCREEN("/fxml/UpdateNewspaperScreen.fxml");

    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
