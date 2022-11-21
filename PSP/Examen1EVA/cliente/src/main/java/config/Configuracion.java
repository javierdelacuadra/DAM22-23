package config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class Configuracion {

    private final String apiUrl;

    public Configuracion() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("config.yaml");
        Map<String, Object> obj = yaml.load(inputStream);
        this.apiUrl = (String) obj.get("api_url");
    }
}
