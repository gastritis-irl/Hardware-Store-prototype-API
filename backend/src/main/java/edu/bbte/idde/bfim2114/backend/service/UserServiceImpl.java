package edu.bbte.idde.bfim2114.backend.service;

import edu.bbte.idde.bfim2114.backend.model.User;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryException;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryFactory;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = RepositoryFactory.getInstance().getUserRepository();

    @Override
    public boolean authenticate(String username, String password) {
        try {
            User user = userRepository.findByUserName(username);
            if (user == null) {
                return false;
            }
            return user.getPassword().equals(password);
        } catch (RepositoryException e) {
            log.error("Error while authenticating user: {}", username, e);
            throw new ServiceException("Error while authenticating user", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            return userRepository.findById(id) != null;
        } catch (RepositoryException e) {
            log.error("Error while checking if user exists by id: {}", id, e);
            throw new ServiceException("Error while checking if user exists by id", e);
        }
    }

    @Override
    public boolean register(String username, String password) {
        try {
            if (userRepository.findByUserName(username) != null) {
                return false;
            }
            User user = new User(username, password);
            userRepository.create(user);
            return true;
        } catch (RepositoryException e) {
            log.error("Error while registering user: {}", username, e);
            throw new ServiceException("Error while registering user", e);
        }
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        try {
            User user = userRepository.findByUserName(username);
            if (user == null || !user.getPassword().equals(oldPassword)) {
                return false;
            }
            user.setPassword(newPassword);
            userRepository.update(user);
            return true;
        } catch (RepositoryException e) {
            log.error("Error while changing password for user: {}", username, e);
            throw new ServiceException("Error while changing password for user", e);
        }
    }

    @Override
    public boolean deleteUser(String username, String password) {
        try {
            User user = userRepository.findByUserName(username);
            if (user == null || !user.getPassword().equals(password)) {
                return false;
            }
            userRepository.deleteById(user.getId());
            return true;
        } catch (RepositoryException e) {
            log.error("Error while deleting user: {}", username, e);
            throw new ServiceException("Error while deleting user", e);
        }
    }

    @Override
    public boolean isAdmin(String username) {
        try {
            return "admin".equals(username);
        } catch (RepositoryException e) {
            log.error("Error while checking if user is admin: {}", username, e);
            throw new ServiceException("Error while checking if user is admin", e);
        }
    }
}