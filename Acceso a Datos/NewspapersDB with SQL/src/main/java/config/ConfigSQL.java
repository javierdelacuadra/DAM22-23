package config;

import common.Constantes;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Singleton
public class ConfigSQL {

    private final Properties p;

    private ConfigSQL() {
        Path path = Paths.get(Constantes.SQL_PROPERTIES_PATH);
        p = new Properties();
        InputStream propertiesStream;
        try {
            propertiesStream = Files.newInputStream(path);
            p.loadFromXML(propertiesStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }
}