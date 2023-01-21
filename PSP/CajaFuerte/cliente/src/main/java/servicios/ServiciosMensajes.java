package servicios;

import data.DaoMensajes;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Mensaje;

import java.util.List;

public class ServiciosMensajes {

    private final DaoMensajes dao;

    @Inject
    public ServiciosMensajes(DaoMensajes dao) {
        this.dao = dao;
    }

    public Single<Either<String, List<Mensaje>>> getMensajes(String id) {
        return dao.getAll(id);
    }

    public Single<Either<String, Mensaje>> addMensaje(Mensaje mensaje) {
        return dao.add(mensaje);
    }

    public Single<Either<String, Mensaje>> updateMensaje(Mensaje mensaje) {
        return dao.update(mensaje);
    }

    public Single<Either<String, Boolean>> deleteMensaje(String id) {
        return dao.delete(id);
    }
 }
