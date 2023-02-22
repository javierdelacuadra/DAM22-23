package dominio.serivcios;

import dominio.modelo.Pais;
import dominio.modelo.PaisDetalle;
import io.vavr.control.Either;

import java.util.List;

public interface ServiciosPaises {
    Either<String, List<Pais>> getTodosLosPaises();

    Either<String, Pais> getPais(String pais);

    Either<String, PaisDetalle> getPaisDetalle(String iso);
}
