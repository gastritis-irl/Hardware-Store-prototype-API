package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.repository.jdbc.JDBCRepositoryFactory;

public abstract class RepositoryFactory {

    private static RepositoryFactory instance;
    public abstract UserRepository getUserRepository();

    public static synchronized RepositoryFactory getInstance() {
        if (instance == null) {
            instance = new JDBCRepositoryFactory();
        }

        return instance;
    }
}
