package edu.bbte.idde.bfim2114.backend.repository.jdbc;

import edu.bbte.idde.bfim2114.backend.repository.RepositoryFactory;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;

public class JDBCRepositoryFactory extends RepositoryFactory {
    @Override
    public UserRepository getUserRepository() {
        return new JDBCUserRepository();
    }
}

