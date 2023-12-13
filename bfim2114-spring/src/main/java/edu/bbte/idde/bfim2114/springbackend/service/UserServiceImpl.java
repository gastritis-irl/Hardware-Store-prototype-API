package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.dto.LoginDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.RegisterDTO;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public User findByUsername(String username) {

        log.info("Finding User by username: {}", username);
        return userRepository.findByEmail(username);
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

    @Transactional
    @Override
    public void delete(Long id) {

        log.info("Deleting User by id: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public boolean isAdmin(String username) {
        return "admin".equals(username);
    }

    @Override
    public User registerUser(RegisterDTO registerDTO) {
        log.info("Registering User: {}", registerDTO);
        User user = new User();
        user.setEmail(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User validateUser(LoginDTO loginDTO) {
        log.info("Validating User: {}", loginDTO);
        User user = userRepository.findByEmail(loginDTO.getUsername());
        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) { // Check encoded password
            return user;
        }
        return null;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
