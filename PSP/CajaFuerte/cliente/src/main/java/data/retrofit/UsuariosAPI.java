package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.Usuario;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface UsuariosAPI {

    @GET(ConstantesAPI.USUARIOS_PATH)
    Single<List<Usuario>> getUsers();

    @POST(ConstantesAPI.USUARIOS_PATH)
    Single<Usuario> addUser(@Body Usuario usuario);
}
