package ui.pantallas.common;

public enum Pantallas {
    PANTALLAMAIN("/fxml/PantallaMain.fxml"),
    PANTALLALOGIN("/fxml/Login.fxml"),
    PANTALLABANNERS("/fxml/Busqueda.fxml"),
    PANTALLATIENDA("/fxml/ResultadoUsers.fxml"),
    PANTALLAINICIO("/fxml/Inicio.fxml"),
    PANTALLAFARMEO("/fxml/ResultadoNiveles.fxml"),
    PANTALLAINVENTARIO("/fxml/Inventario.fxml");

    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
