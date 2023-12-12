package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {

        log.info("Finding User by username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User create(User user) {

        log.info("Creating User: {}", user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {

        log.info("Updating User: {}", user);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting User by id: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public boolean isAdmin(String username) {
        return "admin".equals(username);
    }
}
