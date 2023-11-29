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
public class JDBCHardwareRepository implements HardwareRepository {

    private final DataSource connectionManager = DataSourceConfig.getDataSource();

    public JDBCHardwareRepository() {
        try {
            connectionManager.getConnection();
        } catch (SQLException e) {
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!",e);
        }
    }

    @Override
    public Collection<HardwarePart> findAll() throws RepositoryException {
        String sql = "SELECT * FROM hardware_parts";
        Collection<HardwarePart> parts = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                parts.add(fromRow(rs));
            }
        } catch (SQLException e) {
            log.error("HardwarePart search failed!", e);
            throw new RepositoryException("HardwarePart search failed!", e);
        }
        return parts;
    }

    @Override
    public HardwarePart findByPartName(String partName) throws RepositoryException {
        String sql = "SELECT * FROM hardware_parts WHERE name = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, partName);
            ResultSet rs = s.executeQuery();
            boolean exists = rs.next();
            if (!exists) {
                return null;
            }
            return fromRow(rs);
        } catch (SQLException e) {
            log.error("HardwarePart search failed!", e);
            throw new RepositoryException("HardwarePart search failed!", e);
        }
    }

    @Override
    public HardwarePart findById(Long id) throws RepositoryException {
        String sql = "SELECT * FROM hardware_parts WHERE id = ?";
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
            log.error("HardwarePart search failed!", e);
            throw new RepositoryException("HardwarePart search failed!", e);
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        String sql = "DELETE FROM hardware_parts WHERE id=?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement s = conn.prepareStatement(sql)) {
            s.setLong(1, id);
            s.executeUpdate();
        } catch (SQLException e) {
            log.error("HardwarePart deletion failed!", e);
            throw new RepositoryException("HardwarePart deletion failed!", e);
        }
    }

    @Override
    public HardwarePart create(HardwarePart entity) throws RepositoryException {
        String sql = "INSERT INTO hardware_parts VALUES(?, ?, ?, ?, ?, ?, ?)";
        try(Connection conn = connectionManager.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement(sql)){
                s.setLong(1, entity.getId());
                s.setString(2, entity.getName());
                s.setString(3, entity.getManufacturer());
                s.setString(4, entity.getCategory());
                s.setDouble(5, entity.getPrice());
                s.setString(6, entity.getDescription());
                s.setLong(7, entity.getUserId());
                s.executeUpdate();
                return entity;
            } catch (SQLException e) {
                log.error("HardwarePart creation failed!", e);
                throw new RepositoryException("HardwarePart creation failed!",e);
            }
        }catch(SQLException e){
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!",e);
        }
    }

    @Override
    public HardwarePart update(HardwarePart entity) throws RepositoryException {
        String sql = "UPDATE hardware_parts SET name=?, manufacturer=?, category=?, price=?, description=?, userId=? WHERE id=?";
        try(Connection conn = connectionManager.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement(sql)){
                s.setString(1, entity.getName());
                s.setString(2, entity.getManufacturer());
                s.setString(3, entity.getCategory());
                s.setDouble(4, entity.getPrice());
                s.setString(5, entity.getDescription());
                s.setLong(6, entity.getUserId());
                s.setLong(7, entity.getId());
                s.executeUpdate();
                return entity;
            } catch (SQLException e) {
                log.error("HardwarePart update failed!", e);
                throw new RepositoryException("HardwarePart update failed!",e);
            }
        }catch(SQLException e){
            log.error("Connection failed!", e);
            throw new RepositoryException("Connection failed!",e);
        }
    }

    private HardwarePart fromRow(ResultSet rs) throws SQLException {
        HardwarePart part = new HardwarePart();
        part.setId(rs.getLong(1));
        part.setName(rs.getString(2));
        part.setManufacturer(rs.getString(3));
        part.setCategory(rs.getString(4));
        part.setPrice(rs.getDouble(5));
        part.setDescription(rs.getString(6));
        part.setUserId(rs.getLong(7));
        return part;
    }
}
