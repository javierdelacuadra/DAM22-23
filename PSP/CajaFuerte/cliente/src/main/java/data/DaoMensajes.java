package data;

import com.google.gson.Gson;
import data.retrofit.MensajesAPI;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Mensaje;

import java.util.List;

public class DaoMensajes extends DaoGenerics {

    private final MensajesAPI api;

    @Inject
    public DaoMensajes(MensajesAPI api, Gson gson) {
        super(gson);
        this.api = api;
    }

    public Single<Either<String, List<Mensaje>>> getAll(String id) {
        return createSafeSingleApiCall(api.getMensajesByCarpeta(id));
    }

    public Single<Either<String, Mensaje>> add(Mensaje mensaje) {
        return createSafeSingleApiCall(api.addMensaje(mensaje));
    }

    public Single<Either<String, Mensaje>> update(Mensaje mensaje) {
        return createSafeSingleApiCall(api.updateMensaje(mensaje));
    }

    public Single<Either<String, Boolean>> delete(String id) {
        return createSafeSingleDeleteCall(api.deleteMensaje(id));
    }
}
