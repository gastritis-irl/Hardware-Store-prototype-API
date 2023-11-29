package edu.bbte.idde.bfim2114.backend.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bfim2114.backend.util.PropertyProvider;

public final class DataSourceConfig {

    private static volatile HikariDataSource dataSource;

    private DataSourceConfig() {
    }

    public static HikariDataSource getDataSource() {
        HikariDataSource result = dataSource;
        if (result == null) {
            synchronized (DataSourceConfig.class) {
                result = dataSource;
                if (result == null) {
                    result = new HikariDataSource();
                    result.setDriverClassName(PropertyProvider.getProperty("jdbc_driver_class"));
                    result.setJdbcUrl(PropertyProvider.getProperty("jdbc_db_url"));
                    result.setUsername(PropertyProvider.getProperty("jdbc_username"));
                    result.setPassword(PropertyProvider.getProperty("jdbc_password"));
                    result.setMaximumPoolSize(Integer.parseInt(PropertyProvider.getProperty("jdbc_pool_size")));
                    dataSource = result;
                }
            }
        }
        return dataSource;
    }
}
