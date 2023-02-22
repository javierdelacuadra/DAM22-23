package config;


import config.common.ConfigConstants;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Getter
@Log4j2
@Singleton
public class Configuration {


    private String user;
    private String password;


    private String pathNewspapers;
    private String pathArticles;
    private String pathTypes;
    private String pathReaders;

    private String urlDB;

    private String userDB;

    private String passwordDB;

    private String driverDB;


    public Configuration() {

        // get paths
        Properties pathProp = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(ConfigConstants.PATHS_PROPERTIES)) {
            pathProp.load(fileInputStream);
            this.pathNewspapers = pathProp.getProperty(ConfigConstants.NEWSPAPERS_PATH);
            this.pathArticles = pathProp.getProperty(ConfigConstants.ARTICLES_PATH);
            this.pathTypes = pathProp.getProperty(ConfigConstants.TYPES_PATH);
        } catch (FileNotFoundException e) {
            log.error(ConfigConstants.PROPERTIES_FILE_NOT_FOUND);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        // get user and password for login
        Properties p = new Properties();
        try (FileInputStream f = new FileInputStream(ConfigConstants.CONFIG_CONFIG_YML)) {
            p.load(f);
            this.user = p.get(ConfigConstants.USER).toString();
            this.password = p.get(ConfigConstants.PASSWORD).toString();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        // get reader path
        Properties pathReadersProp = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(ConfigConstants.PATH_READERS_PROPERTIES)) {
            pathReadersProp.loadFromXML(fileInputStream);
            this.pathReaders = pathReadersProp.getProperty(ConfigConstants.READERS_PATH);
        } catch (FileNotFoundException e) {
            log.error(ConfigConstants.PROPERTIES_FILE_NOT_FOUND);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }


        // get DB credentials
        Properties dbCredentials = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(ConfigConstants.PATH_DB_CREDENTIALS)) {
            dbCredentials.loadFromXML(fileInputStream);
            this.urlDB = dbCredentials.getProperty(ConfigConstants.URL_DB);
            this.userDB = dbCredentials.getProperty(ConfigConstants.USER_DB);
            this.passwordDB = dbCredentials.getProperty(ConfigConstants.PASSWORD_DB);
            this.driverDB = dbCredentials.getProperty(ConfigConstants.DRIVER_DB);
        } catch (FileNotFoundException e) {
            log.error(ConfigConstants.PROPERTIES_FILE_NOT_FOUND);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
}
