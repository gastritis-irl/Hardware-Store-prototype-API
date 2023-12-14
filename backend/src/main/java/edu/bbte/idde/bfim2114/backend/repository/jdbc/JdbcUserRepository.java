package edu.bbte.idde.bfim2114.backend.repository.jdbc;

import edu.bbte.idde.bfim2114.backend.model.User;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryException;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class JdbcUserRepository implements UserRepository {

    private final DataSource connectionManager = DataSourceConfig.getDataSource();

    public JdbcUserRepository() {
        try {
            connectionManager.getConnection();
        } catch (SQLException e) {
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!", e);
        }
    }

    @Override
    public User create(User entity) {
        String sql = "INSERT INTO users VALUES(?, ?, ?)";
        try (Connection conn = connectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setLong(1, entity.getId());
                preparedStatement.setString(2, entity.getUsername());
                preparedStatement.setString(3, entity.getPassword());
                preparedStatement.executeUpdate();
                return entity;
            } catch (SQLException e) {
                log.error("User creation failed!", e);
                throw new RepositoryException("User creation failed!", e);
            }
        } catch (SQLException e) {
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!", e);
        }
    }

    @Override
    public User update(User entity) {
        String sql = "UPDATE users SET username=?, password=? WHERE id=?";
        try (Connection conn = connectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, entity.getUsername());
                preparedStatement.setString(2, entity.getPassword());
                preparedStatement.setLong(3, entity.getId());
                preparedStatement.executeUpdate();
                return entity;
            } catch (SQLException e) {
                log.error("User update failed!", e);
                throw new RepositoryException("User update failed!", e);
            }
        } catch (SQLException e) {
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!", e);
        }
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return fromRow(resultSet);
            }
            return fromRow(resultSet);
        } catch (SQLException e) {
            log.error("User search failed!", e);
            throw new RepositoryException("User search failed!", e);
        }
    }

    @Override
    public Collection<User> findAll() {
        String sql = "SELECT * FROM users";
        Collection<User> users = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(fromRow(resultSet));
            }
        } catch (SQLException e) {
            log.error("User search failed!", e);
            throw new RepositoryException("User search failed!", e);
        }
        return users;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("User deletion failed!", e);
            throw new RepositoryException("User deletion failed!", e);
        }
    }

    @Override
    public User findByUserName(String userName) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return fromRow(resultSet);
            }
            return fromRow(resultSet);
        } catch (SQLException e) {
            log.error("User search failed!", e);
            throw new RepositoryException("User search failed!", e);
        }
    }

    private User fromRow(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getString(2),
                resultSet.getString(3)
        );
        user.setId(resultSet.getLong(1));
        return user;
    }
}
