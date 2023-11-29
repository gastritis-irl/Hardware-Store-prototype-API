package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.repository.jdbc.JdbcRepositoryFactory;
import edu.bbte.idde.bfim2114.backend.repository.mem.MemRepositoryFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class RepositoryFactory {

    private static RepositoryFactory instance;

    public static synchronized RepositoryFactory getInstance() {
        if (instance == null) {
            String profile = System.getProperty("profile");
            if ("prod".equals(profile)) {
                log.info("Using JDBC repository implementation");
                instance = new JdbcRepositoryFactory();
            } else {
                log.info("Using in-memory repository implementation");
                instance = new MemRepositoryFactory();
            }
        }

        return instance;
    }

    public abstract UserRepository getUserRepository();

    public abstract HardwareRepository getHardwareRepository();
}