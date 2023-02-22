package dominio.serivcios;

import dominio.modelo.Pais;
import dominio.modelo.PaisDetalle;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface ServiciosPaises {
    Single<Either<String, PaisDetalle>> getPaisDetalleLlamadaRetrofitSingle(String iso);

    Single<Either<String, List<Pais>>> getTodosLosPaisesLlamadaRetrofitSingle();
}
