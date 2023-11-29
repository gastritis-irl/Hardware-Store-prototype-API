package edu.bbte.idde.bfim2114.backend.repository.h2;

import edu.bbte.idde.bfim2114.backend.model.User;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryException;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;
import edu.bbte.idde.bfim2114.backend.repository.jdbc.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class H2UserRepository implements UserRepository {

    private final DataSource connectionManager = DataSourceConfig.getDataSource();

    public H2UserRepository() {
        try {
            connectionManager.getConnection();
        } catch (SQLException e) {
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!",e);
        }
    }

    @Override
    public User create(User entity) throws RepositoryException {
        String sql = "INSERT INTO users VALUES(?, ?, ?)";
        try(Connection conn = connectionManager.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement(sql)){
                s.setLong(1, entity.getId());
                s.setString(2, entity.getUsername());
                s.setString(3, entity.getPassword());
                s.executeUpdate();
                return entity;
            } catch (SQLException e) {
                log.error("User creation failed!", e);
                throw new RepositoryException("User creation failed!",e);
            }
        }catch(SQLException e){
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!",e);
        }
    }

    @Override
    public User findById(Long id) throws RepositoryException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setLong(1, id);
            ResultSet rs = s.executeQuery();
            boolean exists = rs.next();
            if (!exists) {
                return null;
            }
            User user = new User(rs.getString("username"), rs.getString("password"));
            user.setId(rs.getLong("id"));

            return user;
        } catch (SQLException e) {
            log.error("User creation failed!", e);
            throw new RepositoryException("User creation failed!",e);
        }
    }

    @Override
    public Collection<User> findAll() throws RepositoryException {
        String sql = "SELECT * FROM users";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            ResultSet rs = s.executeQuery();
            Collection<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            log.error("User creation failed!", e);
            throw new RepositoryException("User creation failed!",e);
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setLong(1, id);
            s.executeUpdate();
        } catch (SQLException e) {
            log.error("User creation failed!", e);
            throw new RepositoryException("User creation failed!",e);
        }
    }

    @Override
    public User findByUserName(String userName) throws RepositoryException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, userName);
            ResultSet rs = s.executeQuery();
            boolean exists = rs.next();
            if (!exists) {
                return null;
            }
            User user = new User(rs.getString("username"), rs.getString("password"));
            user.setId(rs.getLong("id"));

            return user;
        } catch (SQLException e) {
            log.error("User creation failed!", e);
            throw new RepositoryException("User creation failed!",e);
        }
    }
}