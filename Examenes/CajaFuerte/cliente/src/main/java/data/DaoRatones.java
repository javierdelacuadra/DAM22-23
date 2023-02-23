package data;

import com.google.gson.Gson;
import data.retrofit.RatonesAPI;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Raton;

import java.util.List;

public class DaoRatones extends DaoGenerics {

    private final RatonesAPI api;

    @Inject
    public DaoRatones(RatonesAPI api, Gson gson) {
        super(gson);
        this.api = api;
    }

    public Single<Either<String, List<Raton>>> getAll() {
        return createSafeSingleApiCall(api.getRatones());
    }

    public Single<Either<String, Raton>> add(Raton raton) {
        return createSafeSingleApiCall(api.addRaton(raton));
    }
}
