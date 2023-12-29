package edu.bbte.idde.bfim2114.springbackend.controller;

import edu.bbte.idde.bfim2114.springbackend.dto.UserInDTO;
import edu.bbte.idde.bfim2114.springbackend.dto.UserOutDTO;
import edu.bbte.idde.bfim2114.springbackend.mapper.UserMapper;
import edu.bbte.idde.bfim2114.springbackend.model.HardwarePart;
import edu.bbte.idde.bfim2114.springbackend.model.User;
import edu.bbte.idde.bfim2114.springbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{username}")
    public ResponseEntity<UserOutDTO> getUserByUsername(@PathVariable String username) {
        log.info("GET: /api/users/{}", username);
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.userToDTo(user));
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserOutDTO> updateUser(@PathVariable String username,
                                                 @Valid @RequestBody UserInDTO userInDTO) {
        log.info("PUT: /api/users/{}", username);
        User user = userMapper.dtoToUser(userInDTO);
        User updatedUser = userService.update(user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.userToDTo(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("DELETE: /api/users/{}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/hardware")
    public ResponseEntity<Collection<HardwarePart>> getHardwareParts(@PathVariable Long id) {
        log.info("GET: /api/users/{}/hardware", id);
        Collection<HardwarePart> hardwareParts = userService.getHardwareParts(userService.findById(id));
        return ResponseEntity.ok(hardwareParts);
    }
}
