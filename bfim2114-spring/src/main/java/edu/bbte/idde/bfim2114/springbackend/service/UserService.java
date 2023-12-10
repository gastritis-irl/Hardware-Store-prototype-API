package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.model.User;

public interface UserService {

    User findByUsername(String username);

    User create(User user);

    User update(User user);

    void delete(Long id);

    boolean isAdmin(String username);

    boolean existsById(Long id);
}
