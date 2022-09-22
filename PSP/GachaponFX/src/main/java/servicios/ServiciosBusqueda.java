package servicios;

import data.DaoBanner;
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

    public List<ResponseLevelsItem> getNiveles(String text, String difficulty, boolean rated, boolean featured, boolean epic) throws IOException {
        return dao.getNiveles(text, difficulty, rated, featured, epic);
    }
}