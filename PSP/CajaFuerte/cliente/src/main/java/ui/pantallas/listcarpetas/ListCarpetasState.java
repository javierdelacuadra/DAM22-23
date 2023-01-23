package ui.pantallas.listcarpetas;

import lombok.AllArgsConstructor;
import modelo.Carpeta;
import modelo.Mensaje;

import java.util.List;

@AllArgsConstructor
public class ListCarpetasState {
    public List<Carpeta> carpetas;
    public List<Mensaje> mensajes;
    public String error;
}
