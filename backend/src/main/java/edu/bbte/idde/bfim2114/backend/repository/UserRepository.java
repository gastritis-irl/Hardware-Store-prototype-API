package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.model.User;

public interface UserRepository extends BaseRepository<User> {
    User findByUserName(String userName) throws RepositoryException;
    User findById(Long id) throws RepositoryException;
    void deleteById(Long id) throws RepositoryException;
    User create(User entity) throws RepositoryException;
    User update(User entity) throws RepositoryException;

}

