package edu.bbte.idde.bfim2114.backend.repository;

import edu.bbte.idde.bfim2114.backend.model.User;

public interface UserRepository extends BaseRepository<User> {
    User findByUserName(String userName);

    @Override
    User findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    User create(User entity);

    User update(User entity);

}

