package edu.bbte.idde.bfim2114.backend.repository.jdbc;

import edu.bbte.idde.bfim2114.backend.repository.HardwareRepository;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryFactory;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;

public class JdbcRepositoryFactory extends RepositoryFactory {

    @Override
    public UserRepository getUserRepository() {
        return new JdbcUserRepository();
    }

    @Override
    public HardwareRepository getHardwareRepository() {
        return new JdbcHardwareRepository();
    }
}

