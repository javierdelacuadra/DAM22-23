package ui.pantallas.common;

public enum Pantallas {
    PANTALLAMAIN("/fxml/PantallaMain.fxml"),
    PANTALLALOGIN("/fxml/PantallaLogin.fxml"),
    PANTALLAADMINPRODUCTOS("/fxml/PantallaAdminProductos.fxml"),
    PANTALLAADMINCLIENTES("/fxml/PantallaAdminClientes.fxml"),
    PANTALLACLIENTE("/fxml/PantallaCliente.fxml"),
    PANTALLACONFIG("/fxml/PantallaConfig.fxml");

    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
