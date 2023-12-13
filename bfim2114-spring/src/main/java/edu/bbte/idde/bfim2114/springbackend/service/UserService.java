package edu.bbte.idde.bfim2114.springbackend.service;

import edu.bbte.idde.bfim2114.springbackend.dto.LoginDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.RegisterDTO;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User create(User user);

    User update(User user);

    void delete(Long id);

    boolean isAdmin(String username);

    boolean existsById(Long id);

    User registerUser(RegisterDTO registerDTO);

    User validateUser(LoginDTO loginDTO);

    UserDetails loadUserByUsername(String username);
}
