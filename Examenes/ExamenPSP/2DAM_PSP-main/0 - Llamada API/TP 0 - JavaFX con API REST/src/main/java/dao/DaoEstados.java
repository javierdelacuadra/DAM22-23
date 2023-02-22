package dao;

import dominio.modelo.Estado;
import dominio.modelo.Pais;
import io.vavr.control.Either;

import java.util.List;

public interface DaoEstados {
    Either<String, List<Estado>> getEstadosPorPais(Pais paisSeleccionado);
}
