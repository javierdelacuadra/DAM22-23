package data.retrofit;

import io.reactivex.rxjava3.core.Single;
import modelo.Usuario;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface UsuariosAPI {
    @GET("usuarios")
    Single<List<Usuario>> getUsers();

    @POST("usuarios")
    Single<Usuario> addUser(@Body Usuario usuario);
}
