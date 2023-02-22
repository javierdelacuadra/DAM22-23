package org.servidor.config;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.servidor.config.common.ConfigConstants;

import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuration {


    //KeyStore FILE
    private String pathKeystore;
    private String passwordKeystore;
    public Configuration() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream(ConfigConstants.PATH_KEYSTORE_CONFIG));
            this.pathKeystore = prop.getProperty(ConfigConstants.PATH_KEYSTORE);
            this.passwordKeystore = prop.getProperty(ConfigConstants.KEYSTORE_PASSWORD);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }
}
