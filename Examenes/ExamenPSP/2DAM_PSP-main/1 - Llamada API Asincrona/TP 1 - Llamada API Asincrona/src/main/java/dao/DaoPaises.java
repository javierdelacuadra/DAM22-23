package dao;

import dominio.modelo.Pais;
import dominio.modelo.PaisDetalle;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;

import java.util.List;

public interface DaoPaises {
    Single<Either<String, List<Pais>>> getTodosLosPaisesLlamadaRetrofitSingle();

    Single<Either<String, PaisDetalle>> getPaisDetalleLlamadaRetrofitSingle(String iso);
}
