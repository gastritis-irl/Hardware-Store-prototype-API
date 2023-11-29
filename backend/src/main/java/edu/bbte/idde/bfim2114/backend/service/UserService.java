package edu.bbte.idde.bfim2114.backend.service;

public interface UserService {
    boolean authenticate(String username, String password);

    boolean register(String username, String password);

    boolean changePassword(String username, String oldPassword, String newPassword);

    boolean deleteUser(String username, String password);

    boolean isAdmin(String username);

    boolean existsById(Long id);
}
