package servicios;

import data.DaoBanner;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.ResponseLevelsItem;

import java.io.IOException;
import java.util.List;

public class ServiciosBusqueda {

    private final DaoBanner dao;

    @Inject
    public ServiciosBusqueda(DaoBanner dao) {
        this.dao = dao;
    }

    public Either<String, List<ResponseLevelsItem>> getNiveles(String text, String difficulty) throws IOException {
        return dao.getNiveles(text, difficulty);
    }
}