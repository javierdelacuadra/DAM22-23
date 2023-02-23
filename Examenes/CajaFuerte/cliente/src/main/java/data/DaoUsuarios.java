package data;

import com.google.gson.Gson;
import data.retrofit.UsuariosAPI;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Usuario;

import java.util.List;

public class DaoUsuarios extends DaoGenerics {
    private final UsuariosAPI usuariosAPI;

    @Inject
    public DaoUsuarios(UsuariosAPI usuariosAPI, Gson gson) {
        super(gson);
        this.usuariosAPI = usuariosAPI;
    }

    public Single<Either<String, List<Usuario>>> getAll() {
        return createSafeSingleApiCall(usuariosAPI.getUsers());
    }

    public Single<Either<String, Usuario>> add(Usuario usuario) {
        return createSafeSingleApiCall(usuariosAPI.addUser(usuario));
    }
}