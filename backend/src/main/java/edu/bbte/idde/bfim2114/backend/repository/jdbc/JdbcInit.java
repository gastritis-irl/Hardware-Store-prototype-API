package edu.bbte.idde.bfim2114.backend.repository.jdbc;

import edu.bbte.idde.bfim2114.backend.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class JdbcInit {

    private final DataSource connectionManager = DataSourceConfig.getDataSource();

    public JdbcInit() {
        try {
            connectionManager.getConnection();
        } catch (SQLException e) {
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!", e);
        }
    }

    public void init() {
        createUsersTable();
        createHardwarePartsTable();
    }

    private void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                + "username VARCHAR(255),"
                + "password VARCHAR(255)"
                + ")";
        executeUpdate(sql);
    }

    private void createHardwarePartsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS hardware_parts ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(255),"
                + "manufacturer VARCHAR(255),"
                + "category VARCHAR(255),"
                + "price DOUBLE,"
                + "description VARCHAR(255),"
                + "userId BIGINT,"
                + "FOREIGN KEY (userId) REFERENCES users(id)"
                + ")";
        executeUpdate(sql);
    }

    private void executeUpdate(String sql) {
        try (Connection conn = connectionManager.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("Table creation failed!", e);
            throw new RepositoryException("Table creation failed!", e);
        }
    }
}