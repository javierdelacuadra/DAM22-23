package servicios;

import data.DaoBanner;
import jakarta.inject.Inject;
import modelo.Banner;
import modelo.Personaje;

import java.util.List;

public class ServiciosBusqueda {

    private final DaoBanner dao;

    @Inject
    public ServiciosBusqueda(DaoBanner dao) {
        this.dao = dao;
    }

    public void asjdfaos(String text, String difficulty, boolean rated, boolean featured, boolean epic) {
        //return dao.cosa();
    }
}