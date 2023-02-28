package org.example.config;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.example.config.common.ConfigConstants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuration {

    private String urlDB;
    private String database;

    private String collection;

    public Configuration() {

        // get DB credentials
        Properties dbCredentials = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(ConfigConstants.PATH_DB_CREDENTIALS)) {
            dbCredentials.loadFromXML(fileInputStream);
            this.urlDB = dbCredentials.getProperty(ConfigConstants.URL_DB);
            this.database = dbCredentials.getProperty(ConfigConstants.USER_DB);
            this.collection = dbCredentials.getProperty(ConfigConstants.PASSWORD_DB);
        } catch (FileNotFoundException e) {
            log.error(ConfigConstants.PROPERTIES_FILE_NOT_FOUND);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}

