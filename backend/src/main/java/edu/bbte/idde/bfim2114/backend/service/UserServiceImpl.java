package edu.bbte.idde.bfim2114.backend.service;

import edu.bbte.idde.bfim2114.backend.model.User;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
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