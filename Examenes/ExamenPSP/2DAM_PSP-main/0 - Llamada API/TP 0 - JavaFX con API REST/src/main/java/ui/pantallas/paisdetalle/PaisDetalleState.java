package ui.pantallas.paisdetalle;


import dominio.modelo.Pais;
import dominio.modelo.PaisDetalle;
import lombok.Data;

import java.util.List;

@Data
public class PaisDetalleState {

    private String error;
    private List<Pais> paisesList;
    private PaisDetalle paisDetalle;

    public PaisDetalleState(String error, List<Pais> paisesList, PaisDetalle paisDetalle) {
        this.error = error;
        this.paisesList = paisesList;
        this.paisDetalle = paisDetalle;
    }
}
