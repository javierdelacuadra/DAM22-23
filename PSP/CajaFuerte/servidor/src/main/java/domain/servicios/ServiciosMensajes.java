package domain.servicios;

import dao.DaoMensajes;
import domain.servicios.common.ConstantesServicios;
import jakarta.inject.Inject;
import modelo.Mensaje;

import java.util.List;

public class ServiciosMensajes {

    private final DaoMensajes dao;

    @Inject
    public ServiciosMensajes(DaoMensajes dao) {
        this.dao = dao;
    }

    public List<Mensaje> getMensajesByUsuario(String id, String password) {
        int IDUsuario;
        try {
            IDUsuario = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(ConstantesServicios.EL_ID_DEL_USUARIO_DEBE_SER_UN_NUMERO);
        }
        return dao.getMensajesByCarpeta(IDUsuario, password);
    }

    public Mensaje addMensaje(Mensaje mensaje) {
        return dao.addMensaje(mensaje);
    }

    public Mensaje updateMensaje(Mensaje mensaje) {
        return dao.updateMensaje(mensaje);
    }

    public void deleteMensaje(String id) {
        int IDMensaje;
        try {
            IDMensaje = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(ConstantesServicios.EL_ID_DEL_MENSAJE_DEBE_SER_UN_NUMERO);
        }
        dao.deleteMensaje(IDMensaje);
    }
}
