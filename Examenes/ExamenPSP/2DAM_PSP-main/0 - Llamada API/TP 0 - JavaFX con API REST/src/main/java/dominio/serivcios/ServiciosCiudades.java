package dominio.serivcios;

import dominio.modelo.Ciudad;
import dominio.modelo.Estado;
import dominio.modelo.Pais;
import io.vavr.control.Either;

import java.io.IOException;
import java.util.List;

public interface ServiciosCiudades {
    Either<String, List<Ciudad>> getCiudadesPorEstado(Estado estado, Pais paisSeleccionado) throws IOException;
}
