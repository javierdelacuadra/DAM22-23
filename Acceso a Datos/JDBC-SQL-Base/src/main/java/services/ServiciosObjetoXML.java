package services;

import dao.DaoObjetoXML;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBException;
import model.Objeto;

import java.util.List;

public class ServiciosObjetoXML {

    private final DaoObjetoXML dao;

    @Inject
    public ServiciosObjetoXML(DaoObjetoXML dao) {
        this.dao = dao;
    }

    public Either<String, List<Objeto>> getObjetos() throws JAXBException {
        return dao.getObjetos();
    }

    public Either<String, Boolean> saveObjeto(Objeto objeto) throws JAXBException {
        List<Objeto> objetos = dao.getObjetos().get();
        objetos.add(objeto);
        return dao.saveObjetos(objetos);
    }

    public Either<String, Boolean> deleteObjeto(Objeto objeto) throws JAXBException {
        List<Objeto> objetos = dao.getObjetos().get();
        objetos.remove(objeto);
        return dao.saveObjetos(objetos);
    }
}