package services;

import dao.DaoObjetoSpring;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Objeto;

import java.util.List;

public class ServiciosObjetoSpring {

    private final DaoObjetoSpring dao;

    @Inject
    public ServiciosObjetoSpring(DaoObjetoSpring dao) {
        this.dao = dao;
    }

    public Either<Integer, List<Objeto>> getObjetos() {
        return dao.getAll();
    }

    public int addObjeto(Objeto objeto) {
        return dao.save(objeto);
    }

    public int deleteObjeto(Integer id) {
        return dao.remove(id);
    }

    public int updateObjeto(Objeto objeto) {
        return dao.update(objeto);
    }
}