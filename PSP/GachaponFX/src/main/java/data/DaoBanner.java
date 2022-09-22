package data;

import com.google.gson.JsonSyntaxException;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.ResponseLevels;
import modelo.ResponseLevelsItem;
import retrofit.GDApi;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
public class DaoBanner {

    private final GDApi gdApi;

    @Inject
    public DaoBanner(GDApi gdApi) {
        this.gdApi = gdApi;
    }

    public List<ResponseLevelsItem> getNiveles(String text, String difficulty, boolean rated, boolean featured, boolean epic) throws IOException {
        try {
            Response<ResponseLevels> response = gdApi.getNiveles(text, difficulty, rated, featured, epic).execute();
            assert response.body() != null;
            return response.body().getResponseLevelsList();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}