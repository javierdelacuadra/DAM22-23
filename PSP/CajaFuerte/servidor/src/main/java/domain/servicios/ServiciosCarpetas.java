package domain.servicios;

import dao.DaoCarpetas;
import jakarta.inject.Inject;
import modelo.Carpeta;

import java.util.List;

public class ServiciosCarpetas {

    private final DaoCarpetas dao;

    @Inject
    public ServiciosCarpetas(DaoCarpetas dao) {
        this.dao = dao;
    }


    public List<Carpeta> getCarpetasByUsuario(String id) {
        int IDUsuario;
        try {
            IDUsuario = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El id del usuario debe ser un número");
            //TODO: Crear una excepción propia
        }
        return dao.getCarpetasByUsuario(IDUsuario);
    }

    public Carpeta addCarpeta(Carpeta carpeta) {
        return dao.addCarpeta(carpeta);
    }
}
