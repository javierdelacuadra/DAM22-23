package data;

import com.google.gson.Gson;
import config.Configuracion;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Database {

    private final Gson gson;
    private final Configuracion configuracion;

    @Inject
    public Database(Gson gson, Configuracion configuracion) {
        this.gson = gson;
        this.configuracion = configuracion;
    }
}