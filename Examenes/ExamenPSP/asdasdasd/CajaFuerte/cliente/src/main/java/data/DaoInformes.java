package data;

import com.google.gson.Gson;
import data.retrofit.InformesAPI;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Informe;

import java.util.List;

public class DaoInformes extends DaoGenerics {

    private final InformesAPI api;

    @Inject
    public DaoInformes(InformesAPI api, Gson gson) {
        super(gson);
        this.api = api;
    }

    public Single<Either<String, List<Informe>>> getAll() {
        return createSafeSingleApiCall(api.getInformes());
    }

    public Single<Either<String, Informe>> add(Informe informe) {
        return createSafeSingleApiCall(api.addInforme(informe));
    }

    public Single<Either<String, Informe>> getInforme(String nombre) {
        return createSafeSingleApiCall(api.getInforme(nombre));
    }
}