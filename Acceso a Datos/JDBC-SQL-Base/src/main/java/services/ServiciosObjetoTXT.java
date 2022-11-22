package services;

import dao.DaoObjetoTXT;
import jakarta.inject.Inject;
import model.Objeto;

import java.util.List;

public class ServiciosObjetoTXT {

    private final DaoObjetoTXT dao;

    @Inject
    public ServiciosObjetoTXT(DaoObjetoTXT dao) {
        this.dao = dao;
    }

    public List<Objeto> getObjetos() {
        return dao.getObjetos();
    }

    public boolean saveObjeto(Objeto objeto) {
        return dao.saveObjeto(objeto);
    }
}