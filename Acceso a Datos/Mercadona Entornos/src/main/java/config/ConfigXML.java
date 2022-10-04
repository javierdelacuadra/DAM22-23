package config;

import java.io.IOException;
import java.util.Properties;

public class ConfigXML {
    private static ConfigXML instance = null;
    private Properties p;

    private ConfigXML() {
        try {
            p = new Properties();
            p.loadFromXML(ConfigTXT.class.getClassLoader().getResourceAsStream("configFiles/properties.xml"));
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
