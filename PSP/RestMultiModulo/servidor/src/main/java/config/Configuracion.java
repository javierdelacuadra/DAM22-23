package config;

import jakarta.inject.Singleton;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Singleton
public class Configuracion {

    private final String urlDB;
    private final String user_name;
    private final String password;
    private final String driver;

    private Configuracion() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("config.yaml");
        Map<String, Object> obj = yaml.load(inputStream);
        this.urlDB = (String) obj.get("urlDB");
        this.user_name = (String) obj.get("user_name");
        this.password = (String) obj.get("password");
        this.driver = (String) obj.get("driver");
    }

    public String getProperty(String property) {
        return switch (property) {
            case "urlDB" -> urlDB;
            case "user_name" -> user_name;
            case "password" -> password;
            case "driver" -> driver;
            default -> null;
        };
    }
}
