package config.common;

public class ConfigConstants {

    public static final String PROTOCOL = "protocol";
    public static final String HOST = "host";
    public static final String FROM = "from";
    public static final String PASSWORD = "password";

    private ConfigConstants() {
    }


    public static final String PATH_EMAIL_CONFIG = "config/email.properties";
    public static final String PATH_DB_CREDENTIALS = "config/DBCredentials.properties";

    public static final String URL_DB = "urlDB";
    public static final String USER_DB = "userDB";
    public static final String PASSWORD_DB = "passwordDB";
    public static final String DRIVER_DB = "driverDB";

    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    public static final String PROPERTIES_FILE_NOT_FOUND = "*.properties file not found";
}
