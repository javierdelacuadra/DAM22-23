package servicios;

import data.DaoMensajes;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Carpeta;
import modelo.Mensaje;
import org.example.seguridad.Encriptacion;

import java.util.Base64;
import java.util.List;

public class ServiciosMensajes {

    private final DaoMensajes dao;
    private final Encriptacion encriptacion;

    @Inject
    public ServiciosMensajes(DaoMensajes dao, Encriptacion encriptacion) {
        this.dao = dao;
        this.encriptacion = encriptacion;
    }

    public Single<Either<String, List<Mensaje>>> getMensajes(Carpeta carpeta) {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedPassword = encoder.encodeToString(carpeta.getPassword().getBytes());
        carpeta.setPassword(encodedPassword);
        Single<Either<String, List<Mensaje>>> mensajes = dao.getAll(carpeta);
        return mensajes.map(either -> {
            if (either.isRight()) {
                List<Mensaje> mensajesDesencriptados = either.get();
                carpeta.setPassword(new String(Base64.getDecoder().decode(carpeta.getPassword())));
                for (Mensaje mensaje : mensajesDesencriptados) {
                    String contenidoDesencriptado = encriptacion.desencriptar(mensaje.getContenido(), carpeta.getPassword());
                    mensaje.setContenido(contenidoDesencriptado);
                }
                return Either.right(mensajesDesencriptados);
            } else {
                return Either.left(either.getLeft());
            }
        });
    }

    public Single<Either<String, Mensaje>> addMensaje(Mensaje mensaje, String passCarpeta) {
        String contenidoEncriptado = encriptacion.encriptar(mensaje.getContenido(), passCarpeta);
        mensaje.setContenido(contenidoEncriptado);
        Single<Either<String, Mensaje>> mensajeEncriptado = dao.add(mensaje);
        return mensajeEncriptado.map(either -> {
            if (either.isRight()) {
                Mensaje mensajeDesencriptado = either.get();
                String contenidoDesencriptado = encriptacion.desencriptar(mensajeDesencriptado.getContenido(), passCarpeta);
                mensajeDesencriptado.setContenido(contenidoDesencriptado);
                return Either.right(mensajeDesencriptado);
            } else {
                return Either.left(either.getLeft());
            }
        });
    }

    public Single<Either<String, Mensaje>> updateMensaje(Mensaje mensaje, String passCarpeta) {
        String contenidoEncriptado = encriptacion.encriptar(mensaje.getContenido(), passCarpeta);
        mensaje.setContenido(contenidoEncriptado);
        Single<Either<String, Mensaje>> mensajeEncriptado = dao.update(mensaje);
        return mensajeEncriptado.map(either -> {
            if (either.isRight()) {
                Mensaje mensajeDesencriptado = either.get();
                String contenidoDesencriptado = encriptacion.desencriptar(mensajeDesencriptado.getContenido(), passCarpeta);
                mensajeDesencriptado.setContenido(contenidoDesencriptado);
                return Either.right(mensajeDesencriptado);
            } else {
                return Either.left(either.getLeft());
            }
        });
    }

    public Single<Either<String, Boolean>> deleteMensaje(String id) {
        return dao.delete(id);
    }
 }
