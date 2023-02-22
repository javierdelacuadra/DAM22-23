package ui.pantallas.listadociudades;

import dominio.modelo.Estado;
import lombok.Data;
import dominio.modelo.Ciudad;

import java.util.List;

@Data
public class ListadoCiudadesState {

    private String error;
    private List<Estado> estadoList;
    private List<String> nombrePaises;
    private List<Ciudad> ciudadesList;

    public ListadoCiudadesState(String error, List<String> nombrePaises, List<Estado> estadoList, List<Ciudad> ciudadesList) {
        this.error = error;
        this.nombrePaises = nombrePaises;
        this.estadoList = estadoList;
        this.ciudadesList = ciudadesList;
    }
}
