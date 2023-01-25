package servicios;

import data.DaoUsuarios;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Usuario;

import java.util.List;

public class ServiciosUsuario {

    private final DaoUsuarios daoUsuarios;

    @Inject
    public ServiciosUsuario(DaoUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public Single<Either<String, Usuario>> saveUsuario(Usuario usuario) {
        return daoUsuarios.add(usuario);
    }

    public Single<Either<String, List<Usuario>>> getAllUsers() {
        return daoUsuarios.getAll();
    }

}