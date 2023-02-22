package dominio.serivcios.impl;

import dao.DaoPaises;
import dominio.modelo.PaisDetalle;
import dominio.serivcios.ServiciosPaises;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import dominio.modelo.Pais;

import java.util.List;

public class ServiciosPaisesImpl implements ServiciosPaises {

    private final DaoPaises daoPaisesImpl;

    @Inject
    public ServiciosPaisesImpl(DaoPaises daoPaisesImpl) {
        this.daoPaisesImpl = daoPaisesImpl;
    }

    @Override
    public Single<Either<String, PaisDetalle>> getPaisDetalleLlamadaRetrofitSingle(String iso) {
        return daoPaisesImpl.getPaisDetalleLlamadaRetrofitSingle(iso);
    }

    @Override
    public Single<Either<String, List<Pais>>> getTodosLosPaisesLlamadaRetrofitSingle() {
        return daoPaisesImpl.getTodosLosPaisesLlamadaRetrofitSingle();
    }
}
