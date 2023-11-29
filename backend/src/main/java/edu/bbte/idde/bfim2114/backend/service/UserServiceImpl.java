package edu.bbte.idde.bfim2114.backend.service;

import edu.bbte.idde.bfim2114.backend.model.User;
import edu.bbte.idde.bfim2114.backend.repository.RepositoryFactory;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = RepositoryFactory.getInstance().getUserRepository();

    @Override
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.findById(id) != null;
    }

    @Override
    public boolean register(String username, String password) {
        if (userRepository.findByUserName(username) != null) {
            return false;
        }
        User user = new User(username, password);
        userRepository.create(user);
        return true;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUserName(username);
        if (user == null || !user.getPassword().equals(oldPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        userRepository.update(user);
        return true;
    }

    @Override
    public boolean deleteUser(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user == null || !user.getPassword().equals(password)) {
            return false;
        }
        userRepository.deleteById(user.getId());
        return true;
    }

    @Override
    public boolean isAdmin(String username) {
        return username.equals("admin");
    }
}