package edu.bbte.idde.bfim2114.backend.repository.mem;

import edu.bbte.idde.bfim2114.backend.repository.HardwareRepository;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryFactory;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;

public class MemRepositoryFactory extends RepositoryFactory {
    @Override
    public UserRepository getUserRepository() {
        return MemUserRepository.getInstance();
    }

    @Override
    public HardwareRepository getHardwareRepository() {
        return MemHardwareRepository.getInstance();
    }
}