package config;

import config.common.ConstantesConfig;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuracion {


    private String apiKey;

    private String urlApi;

    public Configuracion() {
        try {
            Properties p = new Properties();
            p.load(Configuracion.class.getClassLoader().getResourceAsStream(ConstantesConfig.CONFIG_FILE));
            this.apiKey = p.get(ConstantesConfig.API_KEY).toString();
            this.urlApi = p.get(ConstantesConfig.URL_API).toString();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
