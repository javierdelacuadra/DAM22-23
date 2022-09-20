package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Getter
@Log4j2
@Singleton
public class Configuracion {

    private Configuracion() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        try {
            Configuracion configuracion = mapper.readValue(
                    Configuracion.class.getClassLoader().getResourceAsStream("config.yaml"), Configuracion.class);

            this.pathClientes = configuracion.getPathClientes();
            this.pathProductos = configuracion.getPathProductos();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String pathClientes;
    private String pathProductos;

}