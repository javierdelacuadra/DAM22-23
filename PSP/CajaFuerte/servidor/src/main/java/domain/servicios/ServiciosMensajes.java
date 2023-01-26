package domain.servicios;

import dao.DaoMensajes;
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
            throw new NumberFormatException("El id del usuario debe ser un número");
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
            throw new NumberFormatException("El id del mensaje debe ser un número");
        }
        dao.deleteMensaje(IDMensaje);
    }
}
