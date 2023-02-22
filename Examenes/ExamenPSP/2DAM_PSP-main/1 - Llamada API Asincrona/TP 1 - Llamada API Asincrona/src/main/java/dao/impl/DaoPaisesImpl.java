package dao.impl;

import dao.DaoPaises;
import dao.common.ConstantesDAO;
import dao.retrofit.llamadas.WorldApi;
import dominio.modelo.PaisDetalle;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import dominio.modelo.Pais;
import java.util.List;

public class DaoPaisesImpl implements DaoPaises {


    private final WorldApi worldApi;

    @Inject
    public DaoPaisesImpl(WorldApi worldApi) {
        this.worldApi = worldApi;
    }

    public Single<Either<String, List<Pais>>> getTodosLosPaisesLlamadaRetrofitSingle() {
        return worldApi.getAllCountriesAsync()
                .map(responsePaisItemList -> {
                    List<Pais> misPaises = responsePaisItemList.stream().map(paisItem -> new Pais(paisItem.getId(), paisItem.getName(), paisItem.getIso2())).toList();
                    return Either.right(misPaises).mapLeft(Object::toString);
                })
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> Either.left(ConstantesDAO.ERROR_EN_LA_REQUEST + ConstantesDAO.BREAK + throwable.getMessage()));
    }


    @Override
    public Single<Either<String, PaisDetalle>> getPaisDetalleLlamadaRetrofitSingle(String iso) {
        return worldApi.getPaisDetalleAsync(iso)
                .map(respPais -> {
                    PaisDetalle miPais = new PaisDetalle(respPais.getName(), respPais.getCapital(), respPais.getRegion(),
                            (respPais.getCurrencyName() + ConstantesDAO.COMA + respPais.getCurrency() + ConstantesDAO.SPACE + respPais.getCurrencySymbol())
                            , respPais.getPhonecode(), respPais.getTld());
                    return Either.right(miPais).mapLeft(Object::toString);
                })
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> Either.left(ConstantesDAO.ERROR_EN_LA_REQUEST + ConstantesDAO.BREAK + throwable.getMessage()));
    }
}
