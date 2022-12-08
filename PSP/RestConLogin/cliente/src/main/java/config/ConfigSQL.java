package config;

import common.Constantes;
import jakarta.inject.Singleton;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Getter
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