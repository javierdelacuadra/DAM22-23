package org.cliente.ui.getAllJavaSimpleFX.common;

public enum Pantallas {


    PRINCIPAL(ConstantesPantallas.FXML_PRINCIPAL_FXML);
    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }


}
