package data;

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
            try {
                Response<List<ResponseLevelsItem>> response = gdApi.getNiveles(text).execute();
                if (response.isSuccessful()) {
                    return Either.right(response.body());
                } else {
                    if (response.errorBody().string().equals("-1")) {
                        return Either.left("No se han encontrado niveles");
                    } else {
                        return Either.left("Error desconocido");
                    }
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return Either.left("Error: \nNo se pudo conectar con el servidor");
            }
        } else {
            try {
                Response<List<ResponseLevelsItem>> response = gdApi.getNiveles(text, difficulty).execute();
                if (response.isSuccessful()) {
                    return Either.right(response.body());
                } else {
                    if (response.errorBody().string().equals("-1")) {
                        return Either.left("No se han encontrado niveles");
                    } else {
                        return Either.left("Error desconocido");
                    }
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return Either.left("Error: \nNo se pudo conectar con el servidor");
            }
        }
    }
}