package data;

import data.common.Constantes;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.ResponseLevelsItem;
import retrofit.GDApi;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Log4j2
public class DaoNiveles {

    private final GDApi gdApi;

    @Inject
    public DaoNiveles(GDApi gdApi) {
        this.gdApi = gdApi;
    }

    public Either<String, List<ResponseLevelsItem>> getNiveles(String text, String difficulty) {
        if (difficulty.equals("")) {
            return getNivelSinFiltro(text);
        } else {
            return getNivelConFiltro(text, difficulty);
        }
    }

    public Either<String, List<ResponseLevelsItem>> getNivelSinFiltro(String text) {
        try {
            Response<List<ResponseLevelsItem>> response = gdApi.getNiveles(text).execute();
            return getLists(response);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constantes.ERROR_NO_SE_PUDO_CONECTAR_CON_EL_SERVIDOR);
        }
    }

    public Either<String, List<ResponseLevelsItem>> getNivelConFiltro(String text, String difficulty) {
        try {
            Response<List<ResponseLevelsItem>> response = gdApi.getNiveles(text, difficulty).execute();
            return getLists(response);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(Constantes.ERROR_NO_SE_PUDO_CONECTAR_CON_EL_SERVIDOR);
        }
    }

    private Either<String, List<ResponseLevelsItem>> getLists(Response<List<ResponseLevelsItem>> response) throws IOException {
        if (response.isSuccessful()) {
            return Either.right(response.body());
        } else {
            assert response.errorBody() != null;
            if (response.errorBody().string().equals(Constantes.SERVER_BAD_RESPONSE)) {
                return Either.left(Constantes.ERROR_NO_HAY_NIVELES);
            } else {
                return Either.left(Constantes.ERROR_DESCONOCIDO);
            }
        }
    }
}