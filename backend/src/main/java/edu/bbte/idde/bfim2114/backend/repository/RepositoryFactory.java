package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.repository.jdbc.JDBCRepositoryFactory;
import edu.bbte.idde.bfim2114.backend.repository.mem.MemRepositoryFactory;

public abstract class RepositoryFactory {

    private static RepositoryFactory instance;

    public static synchronized RepositoryFactory getInstance() {
        if (instance == null) {
            String profile = System.getProperty("profile");
            if ("mysql".equals(profile)) {
                instance = new JDBCRepositoryFactory();
            } else {
                instance = new MemRepositoryFactory();
            }
        }

        return instance;
    }

    public abstract UserRepository getUserRepository();

    public abstract HardwareRepository getHardwareRepository();
}