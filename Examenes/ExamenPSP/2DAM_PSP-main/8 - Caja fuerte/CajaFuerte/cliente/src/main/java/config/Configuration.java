package config;


import config.common.ConfigConstants;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuration {


    private String apiURL;
    public Configuration() {


        Properties apiCredential = new Properties();
        try {
            apiCredential.loadFromXML(getClass().getResourceAsStream(ConfigConstants.CONFIG_API_PATH_XML));
            this.apiURL = apiCredential.getProperty(ConfigConstants.APIURL);
        } catch (FileNotFoundException e) {
            log.error(ConfigConstants.PROPERTIES_FILE_NOT_FOUND);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
}
