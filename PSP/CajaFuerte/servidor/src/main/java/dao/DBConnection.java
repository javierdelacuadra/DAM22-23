package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import config.Configuracion;
import dao.common.ConstantesDBConnection;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class DBConnection {
    private final Configuracion config;
    private final DataSource hikariDataSource;

    @Inject
    public DBConnection(Configuracion config) {
        this.config = config;
        hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getProperty(ConstantesDBConnection.URL_DB));
        hikariConfig.setUsername(config.getProperty(ConstantesDBConnection.USER_NAME));
        hikariConfig.setPassword(config.getProperty(ConstantesDBConnection.PASSWORD));
        hikariConfig.setDriverClassName(config.getProperty(ConstantesDBConnection.DRIVER));
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty(ConstantesDBConnection.CACHE_PREP_STMTS, true);
        hikariConfig.addDataSourceProperty(ConstantesDBConnection.PREP_STMT_CACHE_SIZE, 250);
        hikariConfig.addDataSourceProperty(ConstantesDBConnection.PREP_STMT_CACHE_SQL_LIMIT, 2048);

        return new HikariDataSource(hikariConfig);
    }

    public DataSource getHikariDataSource() {
        return hikariDataSource;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }
}
