package dominio.serivcios;

import dominio.modelo.Estado;
import dominio.modelo.Pais;
import io.vavr.control.Either;

import java.util.List;

public interface ServiciosEstados {
    Either<String, List<Estado>> getEstadosPorPais(Pais paisSeleccionado);
}
