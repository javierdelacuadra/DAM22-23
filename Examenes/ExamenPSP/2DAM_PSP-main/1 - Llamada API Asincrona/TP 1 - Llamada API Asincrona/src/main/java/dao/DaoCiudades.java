package dao;

import dominio.modelo.Ciudad;
import dominio.modelo.Estado;
import dominio.modelo.Pais;
import io.vavr.control.Either;

import java.util.List;

public interface DaoCiudades {
    Either<String, List<Ciudad>> getCiudadesPorEstado(Estado estado, Pais paisSeleccionado);
}
