package data;

import com.google.gson.Gson;
import data.retrofit.CarpetasAPI;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Carpeta;

import java.util.List;

public class DaoCarpetas extends DaoGenerics {

    private final CarpetasAPI api;

    @Inject
    public DaoCarpetas(CarpetasAPI api, Gson gson) {
        super(gson);
        this.api = api;
    }

    public Single<Either<String, List<Carpeta>>> getAll(String id) {
        return createSafeSingleApiCall(api.getCarpetasByUsuario(id));
    }

    public Single<Either<String, Carpeta>> add(Carpeta carpeta) {
        return createSafeSingleApiCall(api.addCarpeta(carpeta));
    }
}
