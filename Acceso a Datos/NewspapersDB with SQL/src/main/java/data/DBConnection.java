package data;

import config.ConfigSQL;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final ConfigSQL config;

    @Inject
    public DBConnection(ConfigSQL config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {

        Connection connection = DriverManager
                .getConnection(config.getProperty("urlDB"), config.getProperty("user_name"), config.getProperty("password"));
        System.out.println("Connected to DB");
        return connection;
    }

    public void closeConnection(Connection connArg) {
        System.out.println("Releasing all open resources ...");
        try {
            if (connArg != null) {
                connArg.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
