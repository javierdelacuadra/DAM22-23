package servicios;

import data.DaoCarpetas;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Carpeta;
import modelo.Mensaje;
import org.example.seguridad.Encriptacion;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ServiciosCarpetas {

    private final DaoCarpetas dao;
    private final Encriptacion encriptacion;
    private final ServiciosMensajes serviciosMensajes;

    @Inject
    public ServiciosCarpetas(DaoCarpetas dao, Encriptacion encriptacion, ServiciosMensajes serviciosMensajes) {
        this.dao = dao;
        this.encriptacion = encriptacion;
        this.serviciosMensajes = serviciosMensajes;
    }

    public Single<Either<String, List<Carpeta>>> getCarpetas(String id) {
        return dao.getAll(id);
    }

    public Single<Either<String, Carpeta>> addCarpeta(Carpeta carpeta) {
        return dao.add(carpeta);
    }

    public Single<Either<String, Carpeta>> cargarCarpetaCompartida(String nombreUsuario, String nombreCarpeta, String passwordCarpeta) {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedPassword = encoder.encodeToString(passwordCarpeta.getBytes());
        return dao.cargarCarpetaCompartida(nombreUsuario, nombreCarpeta, encodedPassword)
                .map(either -> {
                    if (either.isRight()) {
                        Carpeta carpeta = either.get();
                        List<Mensaje> mensajesDesencriptados = new ArrayList<>();
                        for (Mensaje mensaje : carpeta.getMensajes()) {
                            mensaje.setContenido(encriptacion.desencriptar(mensaje.getContenido(), passwordCarpeta));
                            mensajesDesencriptados.add(mensaje);
                        }
                        carpeta.setMensajes(mensajesDesencriptados);
                    }
                    return either;
                });
    }

    public Single<Either<String, Carpeta>> cambiarPasswordCarpeta(Carpeta carpeta, String nuevaPass) {
        List<Mensaje> mensajes = serviciosMensajes.getMensajes(carpeta).blockingGet().get();
        for (Mensaje mensaje : mensajes) {
            mensaje.setContenido(encriptacion.encriptar(mensaje.getContenido(), nuevaPass));
        }
        carpeta.setMensajes(mensajes);
        carpeta.setPassword(nuevaPass);
        return dao.update(carpeta);

    }
}
