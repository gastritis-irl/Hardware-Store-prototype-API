package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.model.User;

public interface UserRepository extends BaseRepository<User> {
    User findByUserName(String userName) throws RepositoryException;
}

