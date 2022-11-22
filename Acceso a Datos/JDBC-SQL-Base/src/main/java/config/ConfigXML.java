package config;

import dao.common.Constantes;

import java.io.IOException;
import java.util.Properties;

public class ConfigXML {
    private static ConfigXML instance = null;
    private Properties p;

    private ConfigXML() {
        try {
            p = new Properties();
            p.loadFromXML(ConfigXML.class.getClassLoader().getResourceAsStream(Constantes.PROPERTIES_XML));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigXML getInstance() {

        if (instance == null) {
            instance = new ConfigXML();
        }
        return instance;
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }
}
