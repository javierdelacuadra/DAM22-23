package ui.pantallas.listadociudades;

import dominio.modelo.Estado;
import dominio.modelo.Pais;
import lombok.Data;
import dominio.modelo.Ciudad;

import java.util.List;

@Data
public class ListadoCiudadesState {

    private String error;
    private List<Estado> estadoList;
    private List<Pais> paisesList;
    private List<Ciudad> ciudadesList;
    private boolean isLoading;

    public ListadoCiudadesState(String error, List<Pais> paisesList, List<Estado> estadoList, List<Ciudad> ciudadesList, boolean isLoading) {
        this.error = error;
        this.paisesList = paisesList;
        this.estadoList = estadoList;
        this.ciudadesList = ciudadesList;
        this.isLoading = isLoading;
    }
}
