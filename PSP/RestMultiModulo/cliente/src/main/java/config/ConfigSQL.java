package config;

import common.Constantes;
import jakarta.inject.Singleton;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

@Singleton
public class ConfigSQL {

    private final String apiUrl;

    public ConfigSQL() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(Constantes.CONFIG_YAML);
        Map<String, Object> obj = yaml.load(inputStream);
        this.apiUrl = (String) obj.get(Constantes.API_URL);
    }
}