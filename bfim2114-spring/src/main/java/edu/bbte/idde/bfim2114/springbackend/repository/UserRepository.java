package edu.bbte.idde.bfim2114.springbackend.repository;

import edu.bbte.idde.bfim2114.springbackend.model.User;

public interface UserRepository extends BaseRepository<User> {

    User findByEmail(String username);
}
