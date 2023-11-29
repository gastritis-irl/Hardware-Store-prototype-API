package edu.bbte.idde.bfim2114.backend.repository.jdbc;

import edu.bbte.idde.bfim2114.backend.model.HardwarePart;
import edu.bbte.idde.bfim2114.backend.repository.HardwareRepository;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class JdbcHardwareRepository implements HardwareRepository {

    private final DataSource connectionManager = DataSourceConfig.getDataSource();

    public JdbcHardwareRepository() {
        try {
            connectionManager.getConnection();
        } catch (SQLException e) {
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!", e);
        }
    }

    @Override
    public Collection<HardwarePart> findAll() {
        String sql = "SELECT * FROM hardware_parts";
        Collection<HardwarePart> parts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                parts.add(fromRow(resultSet));
            }
        } catch (SQLException e) {
            log.error("HardwarePart search failed!", e);
            throw new RepositoryException("HardwarePart search failed!", e);
        }
        return parts;
    }

    @Override
    public HardwarePart findByPartName(String partName) {
        String sql = "SELECT * FROM hardware_parts WHERE name = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, partName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return fromRow(resultSet);
            }
        } catch (SQLException e) {
            log.error("HardwarePart search failed!", e);
            throw new RepositoryException("HardwarePart search failed!", e);
        }
        return null;
    }

    @Override
    public HardwarePart findById(Long id) {
        String sql = "SELECT * FROM hardware_parts WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return fromRow(resultSet);
            }
        } catch (SQLException e) {
            log.error("HardwarePart search failed!", e);
            throw new RepositoryException("HardwarePart search failed!", e);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM hardware_parts WHERE id=?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("HardwarePart deletion failed!", e);
            throw new RepositoryException("HardwarePart deletion failed!", e);
        }
    }

    @Override
    public HardwarePart create(HardwarePart entity) {
        String sql = "INSERT INTO hardware_parts VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getManufacturer());
            preparedStatement.setString(4, entity.getCategory());
            preparedStatement.setDouble(5, entity.getPrice());
            preparedStatement.setString(6, entity.getDescription());
            preparedStatement.setLong(7, entity.getUserId());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            log.error("HardwarePart creation failed!", e);
            throw new RepositoryException("HardwarePart creation failed!", e);
        }
    }

    @Override
    public HardwarePart update(HardwarePart entity) {
        String sql = "UPDATE hardware_parts SET name=?, manufacturer=?, "
                + "category=?, price=?, description=?, userId=? WHERE id=?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getManufacturer());
            preparedStatement.setString(3, entity.getCategory());
            preparedStatement.setDouble(4, entity.getPrice());
            preparedStatement.setString(5, entity.getDescription());
            preparedStatement.setLong(6, entity.getUserId());
            preparedStatement.setLong(7, entity.getId());
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            log.error("HardwarePart update failed!", e);
            throw new RepositoryException("HardwarePart update failed!", e);
        }
    }

    private HardwarePart fromRow(ResultSet resultSet) throws SQLException {
        HardwarePart part = new HardwarePart();
        part.setId(resultSet.getLong(1));
        part.setName(resultSet.getString(2));
        part.setManufacturer(resultSet.getString(3));
        part.setCategory(resultSet.getString(4));
        part.setPrice(resultSet.getDouble(5));
        part.setDescription(resultSet.getString(6));
        part.setUserId(resultSet.getLong(7));
        return part;
    }
}