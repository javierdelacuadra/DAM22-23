package ui.common;

public enum Pantallas {
    PANTALLAMAIN("/fxml/PantallaMain.fxml"),
    PANTALLAEQUIPOS("/fxml/PantallaEquipos.fxml");

    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
