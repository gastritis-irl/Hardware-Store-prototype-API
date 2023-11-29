package edu.bbte.idde.bfim2114.backend.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bfim2114.backend.util.PropertyProvider;

public class DataSourceConfig {

    private static volatile HikariDataSource dataSource;

    private DataSourceConfig() {
        // Private constructor to prevent instantiation
    }

    public static HikariDataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DataSourceConfig.class) {
                if (dataSource == null) {
                    dataSource = new HikariDataSource();
                    dataSource.setDriverClassName(PropertyProvider.getProperty("jdbc_driver_class"));
                    dataSource.setJdbcUrl(PropertyProvider.getProperty("jdbc_db_url"));
                    dataSource.setMaximumPoolSize(Integer.parseInt(PropertyProvider.getProperty("jdbc_pool_size")));
                }
            }
        }
        return dataSource;
    }
}
