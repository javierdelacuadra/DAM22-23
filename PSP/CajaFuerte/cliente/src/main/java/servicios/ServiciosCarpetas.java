package servicios;

import data.DaoCarpetas;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Carpeta;

import java.util.List;

public class ServiciosCarpetas {

    private final DaoCarpetas dao;

    @Inject
    public ServiciosCarpetas(DaoCarpetas dao) {
        this.dao = dao;
    }

    public Single<Either<String, List<Carpeta>>> getCarpetas(String id) {
        return dao.getAll(id);
    }

    public Single<Either<String, Carpeta>> addCarpeta(Carpeta carpeta) {
        return dao.add(carpeta);
    }
}
