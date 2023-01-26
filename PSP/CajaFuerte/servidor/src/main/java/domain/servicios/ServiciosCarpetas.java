package domain.servicios;

import dao.DaoCarpetas;
import dao.DaoMensajes;
import domain.servicios.common.ConstantesServicios;
import jakarta.inject.Inject;
import modelo.Carpeta;
import modelo.Mensaje;

import java.util.List;

public class ServiciosCarpetas {

    private final DaoCarpetas dao;
    private final DaoMensajes daoMensajes;

    @Inject
    public ServiciosCarpetas(DaoCarpetas dao, DaoMensajes daoMensajes) {
        this.dao = dao;
        this.daoMensajes = daoMensajes;
    }


    public List<Carpeta> getCarpetasByUsuario(String id) {
        int IDUsuario;
        try {
            IDUsuario = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(ConstantesServicios.EL_ID_DEL_USUARIO_DEBE_SER_UN_NUMERO);
        }
        return dao.getCarpetasByUsuario(IDUsuario);
    }

    public Carpeta addCarpeta(Carpeta carpeta) {
        return dao.addCarpeta(carpeta);
    }

    public Carpeta cargarCarpetaCompartida(String nombreCarpeta, String nombreUsuario, String passwordCarpeta) {
        Carpeta carpeta = dao.cargarCarpetaCompartida(nombreCarpeta, nombreUsuario, passwordCarpeta);
        List<Mensaje> mensajes = daoMensajes.getMensajesByCarpeta(carpeta.getId(), carpeta.getPassword());
        carpeta.setMensajes(mensajes);
        return carpeta;
    }

    public Carpeta updateCarpeta(Carpeta carpeta) {
        return dao.updateCarpeta(carpeta);
    }
}
