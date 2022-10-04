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
public class ConfigTXT {

    private ConfigTXT() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        try {
            ConfigTXT configTXT = mapper.readValue(
                    ConfigTXT.class.getClassLoader().getResourceAsStream("config.yaml"), ConfigTXT.class);

            this.pathClientes = configTXT.getPathClientes();
            this.pathProductos = configTXT.getPathProductos();
            this.pathArticles = configTXT.getPathArticles();
            this.pathNewspapers = configTXT.getPathNewspapers();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String pathClientes;
    private String pathProductos;
    private String pathArticles;
    private String pathNewspapers;
}