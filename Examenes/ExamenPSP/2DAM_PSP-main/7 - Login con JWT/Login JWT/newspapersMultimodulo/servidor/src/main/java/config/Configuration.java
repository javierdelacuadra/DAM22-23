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


    //Security
    private String fixedKey;

    private int requestsHoursTimeout;
    private int requestsMinutesTimeout;
    private int requestsSecondsTimeout;
    private int maxReaderPetitions;

    // DB
    private String urlDB;

    private String userDB;

    private String passwordDB;

    private String driverDB;


    // MAIL
    private String port;
    private String auth;
    private String sslTrust;
    private String starttls;
    private String protocol;
    private String host;
    private String from;
    private String password;

    public Configuration() {

        // get DB credentials
        Properties dbCredentials = new Properties();
        try {
            dbCredentials.load(getClass().getClassLoader().getResourceAsStream(ConfigConstants.PATH_DB_CREDENTIALS));
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

        Properties emailConfig = new Properties();
        try {
            emailConfig.load(getClass().getClassLoader().getResourceAsStream(ConfigConstants.PATH_EMAIL_CONFIG));
            this.port = emailConfig.getProperty(ConfigConstants.MAIL_SMTP_PORT);
            this.auth = emailConfig.getProperty(ConfigConstants.MAIL_SMTP_AUTH);
            this.sslTrust = emailConfig.getProperty(ConfigConstants.MAIL_SMTP_SSL_TRUST);
            this.starttls = emailConfig.getProperty(ConfigConstants.MAIL_SMTP_STARTTLS_ENABLE);
            this.protocol = emailConfig.getProperty(ConfigConstants.PROTOCOL);
            this.host = emailConfig.getProperty(ConfigConstants.HOST);
            this.from = emailConfig.getProperty(ConfigConstants.FROM);
            this.password = emailConfig.getProperty(ConfigConstants.PASSWORD);
        } catch (FileNotFoundException e) {
            log.error(ConfigConstants.PROPERTIES_FILE_NOT_FOUND);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        Properties security = new Properties();
        try {
            security.load(getClass().getClassLoader().getResourceAsStream(ConfigConstants.PATH_SECURITY));
            this.fixedKey = security.getProperty(ConfigConstants.FIXED_KEY);
            this.requestsHoursTimeout = Integer.parseInt(security.getProperty(ConfigConstants.REQUESTS_HOURS_TIMEOUT));
            this.requestsMinutesTimeout = Integer.parseInt(security.getProperty(ConfigConstants.REQUESTS_MINUTES_TIMEOUT));
            this.requestsSecondsTimeout = Integer.parseInt(security.getProperty(ConfigConstants.REQUESTS_SECONDS_TIMEOUT));
            this.maxReaderPetitions = Integer.parseInt(security.getProperty(ConfigConstants.MAX_READER_PETITIONS));
        } catch (FileNotFoundException e) {
            log.error(ConfigConstants.PROPERTIES_FILE_NOT_FOUND);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }


    }

}
