package config;

import dao.common.Constantes;
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
                .getResourceAsStream(Constantes.CONFIG_YAML);
        Map<String, Object> obj = yaml.load(inputStream);
        this.urlDB = (String) obj.get(Constantes.URL_DB);
        this.user_name = (String) obj.get(Constantes.USER_NAME);
        this.password = (String) obj.get(Constantes.PASSWORD);
        this.driver = (String) obj.get(Constantes.DRIVER);
    }

    public String getProperty(String property) {
        return switch (property) {
            case Constantes.URL_DB -> urlDB;
            case Constantes.USER_NAME -> user_name;
            case Constantes.PASSWORD -> password;
            case Constantes.DRIVER -> driver;
            default -> null;
        };
    }
}
