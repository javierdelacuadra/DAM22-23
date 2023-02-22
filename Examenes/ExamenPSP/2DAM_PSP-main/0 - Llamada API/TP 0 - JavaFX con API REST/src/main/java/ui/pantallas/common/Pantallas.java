package ui.pantallas.common;

public enum Pantallas {

    LISTADO_CIUDADES(ConstantesPantallas.FXML_LISTADO_CIUDADES_FXML),
    DETALLE(ConstantesPantallas.FXML_DETALLE_PAIS_FXML),

    PRINCIPAL(ConstantesPantallas.FXML_PRINCIPAL_FXML);
    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }


}
