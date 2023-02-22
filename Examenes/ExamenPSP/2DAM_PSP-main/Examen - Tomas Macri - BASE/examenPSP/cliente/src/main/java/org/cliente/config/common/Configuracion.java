package org.cliente.config.common;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.cliente.config.ConstantesConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuracion {



    private String urlApi;

    public Configuracion() {
        try {
            Properties p = new Properties();
            p.load(Configuracion.class.getResourceAsStream(ConstantesConfig.CONFIG_FILE));
            this.urlApi = p.get(ConstantesConfig.URL_API).toString();
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
