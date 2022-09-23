package data;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.ResponseLevelsItem;
import modelo.ResponseUser;
import modelo.Usuario;
import retrofit.GDApi;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
public class DaoFarmeo {

    private final GDApi gdApi;

    @Inject
    public DaoFarmeo(GDApi gdApi) {
        this.gdApi = gdApi;
    }

    public Either<String, ResponseUser> getUser(String username) throws IOException {
        try {
            Response<ResponseUser> response = gdApi.getUser(username).execute();
            if (response.isSuccessful()) {
                return Either.right(response.body());
            } else {
                if (response.errorBody().string().equals("-1")) {
                    return Either.left("No se han encontrado el usuario");
                } else {
                    return Either.left("Error desconocido");
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(e.getMessage());
        }
    }
}