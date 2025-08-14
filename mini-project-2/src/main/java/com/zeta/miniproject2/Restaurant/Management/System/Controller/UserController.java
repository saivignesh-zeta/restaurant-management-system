package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.UserDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.User;
import com.zeta.miniproject2.Restaurant.Management.System.Service.UserService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import com.zeta.miniproject2.Restaurant.Management.System.Util.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        log.info("API Request - Create user: {}", user);
        return new ResponseEntity<>(
                UserMapper.toDTO(userService.createUser(user)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("API Request - Get all users");
        return ResponseEntity.ok(
                userService.getAllUsers()
                        .stream()
                        .map(UserMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        log.info("API Request - Get user ID: {}", id);
        return ResponseEntity.ok(
                UserMapper.toDTO(userService.getUserById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody User user) {
        log.info("API Request - Update (full) user ID: {}", id);
        return ResponseEntity.ok(
                UserMapper.toDTO(userService.updateUser(id, user))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patchUser(@PathVariable Integer id, @RequestBody User user) {
        log.info("API Request - Patch (partial update) user ID: {}", id);
        User existing = userService.getUserById(id);
        existing = EntityUtil.copyNonNullProperties(user, existing);
        return ResponseEntity.ok(
                UserMapper.toDTO(userService.patchUser(id, existing))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.info("API Request - Delete user ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
