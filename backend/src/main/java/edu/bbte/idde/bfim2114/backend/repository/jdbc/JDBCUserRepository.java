package edu.bbte.idde.bfim2114.backend.repository.jdbc;

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
public class JDBCUserRepository implements UserRepository {

    private final DataSource connectionManager = DataSourceConfig.getDataSource();

    public JDBCUserRepository() {
        try {
            connectionManager.getConnection();
        } catch (SQLException e) {
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!",e);
        }
    }

    @Override
    public User create(User entity) {
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
    public User update(User entity) throws RepositoryException {
        String sql = "UPDATE users SET username=?, password=? WHERE id=?";
        try(Connection conn = connectionManager.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement(sql)){
                s.setString(1, entity.getUsername());
                s.setString(2, entity.getPassword());
                s.setLong(3, entity.getId());
                s.executeUpdate();
                return entity;
            } catch (SQLException e) {
                log.error("User update failed!", e);
                throw new RepositoryException("User update failed!",e);
            }
        }catch(SQLException e){
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!",e);
        }
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setLong(1, id);
            ResultSet rs = s.executeQuery();
            boolean exists = rs.next();
            if (!exists) {
                return null;
            }
            return fromRow(rs);
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
             PreparedStatement s = conn.prepareStatement(sql)) {
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                users.add(fromRow(rs));
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
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setLong(1, id);
            s.executeUpdate();
        } catch (SQLException e) {
            log.error("User deletion failed!", e);
            throw new RepositoryException("User deletion failed!", e);
        }
    }

    @Override
    public User findByUserName(String userName) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, userName);
            ResultSet rs = s.executeQuery();
            boolean exists = rs.next();
            if (!exists) {
                return null;
            }
            return fromRow(rs);
        } catch (SQLException e) {
            log.error("User search failed!", e);
            throw new RepositoryException("User search failed!", e);
        }
    }

    private User fromRow(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getString(2),
                rs.getString(3)
        );
        user.setId(rs.getLong(1));
        return user;
    }
}
