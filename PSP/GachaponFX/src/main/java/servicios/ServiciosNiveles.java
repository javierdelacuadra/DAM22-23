package servicios;

import data.DaoNiveles;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.ResponseLevelsItem;

import java.io.IOException;
import java.util.List;

public class ServiciosNiveles {

    private final DaoNiveles dao;

    @Inject
    public ServiciosNiveles(DaoNiveles dao) {
        this.dao = dao;
    }

    public Either<String, List<ResponseLevelsItem>> getNiveles(String text, String difficulty) throws IOException {
        return dao.getNiveles(text, difficulty);
    }
}