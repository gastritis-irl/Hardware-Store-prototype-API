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
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    @Override
    public User validateUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            log.info("Valid user: {}", user);
            return user;
        }
        log.warn("Invalid user: {}", loginDTO);
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        user.setRole("ROLE_" + user.getRole());
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .authorities(user.getRole())
            .build();
    }
}
