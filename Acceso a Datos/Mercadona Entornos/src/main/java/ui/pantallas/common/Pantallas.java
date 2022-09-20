package ui.pantallas.common;

public enum Pantallas {
    PANTALLAMAIN("/fxml/PantallaMain.fxml"),
    PANTALLALOGIN("/fxml/PantallaLogin.fxml"),
    PANTALLAADMINPRODUCTOS("/fxml/PantallaAdminProductos.fxml"),
    PANTALLAADMINCLIENTES("/fxml/PantallaAdminClientes.fxml"),
    PANTALLACLIENTE("/fxml/PantallaCliente.fxml"),
    PANTALLACONFIG("/fxml/PantallaConfig.fxml"),
    LISTNEWSPAPERSCREEN("/fxml/ListNewspaperScreen.fxml"),
    ADDNEWSPAPERSCREEN("/fxml/AddNewspapersScreen.fxml"),
    UPDATENEWSPAPERSCREEN("/fxml/UpdateNewspaperScreen.fxml"),
    DELETENEWSPAPERSCREEN("/fxml/DeleteNewspaperScreen.fxml"),
    LISTARTICLESCREEN("/fxml/ListArticleScreen.fxml"),
    ADDARTICLESCREEN("/fxml/AddArticleScreen.fxml"),
    UPDATEARTICLESCREEN("/fxml/UpdateArticleScreen.fxml"),
    DELETEARTICLESCREEN("/fxml/DeleteArticleScreen.fxml");


    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
