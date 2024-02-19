package edu.bbte.idde.bfim2114.springbackend.controller;

import edu.bbte.idde.bfim2114.springbackend.dto.ThemeDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.UserUpdateDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.UserOutDTO;
import edu.bbte.idde.bfim2114.springbackend.mapper.UserMapper;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.service.JwtService;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @GetMapping("/{id}")
    public ResponseEntity<UserOutDTO> getUserByUsername(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.userToDTo(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutDTO> updateUser(@PathVariable Long id,
                                                 @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        User user = userMapper.dtoToUser(userUpdateDTO);
        user.setId(id);
        User updatedUser = userService.update(user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(updatedUser.getEmail())
            .password(updatedUser.getPassword())
            .authorities(updatedUser.getRole())
            .build();
        String newToken = jwtService.generateToken(userDetails);
        UserOutDTO userOutDTO = userMapper.userToDTo(updatedUser);
        userOutDTO.setToken(newToken);
        return ResponseEntity.ok(userOutDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/hardware")
    public ResponseEntity<Collection<HardwarePart>> getHardwareParts(@PathVariable Long id) {
        Collection<HardwarePart> hardwareParts = userService.getHardwareParts(userService.findById(id));
        return ResponseEntity.ok(hardwareParts);
    }

    @PostMapping("/{id}/theme")
    public ResponseEntity<Void> updateTheme(@RequestBody ThemeDTO themeDTO, @PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            user.setThemeId(themeDTO.getThemeId());
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
