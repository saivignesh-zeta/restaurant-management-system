package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.User;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.UserRole;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .userId(1)
                .name("John Doe")
                .role(UserRole.ADMIN)
                .build();
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User saved = userService.createUser(user);

        assertNotNull(saved);
        assertEquals("John Doe", saved.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByIdFound() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User found = userService.getUserById(1);

        assertNotNull(found);
        assertEquals(1, found.getUserId());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1));
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateUser() {
        User updated = User.builder().name("Jane Doe").role(UserRole.WAITER).build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1, updated);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals(UserRole.WAITER, result.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testPatchUser() {
        User patch = User.builder().role(UserRole.WAITER).build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.patchUser(1, patch);

        assertNotNull(result);
        assertEquals(UserRole.WAITER, result.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUserFound() {
        when(userRepository.existsById(1)).thenReturn(true);

        boolean result = userService.deleteUser(1);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1));
        verify(userRepository, never()).deleteById(anyInt());
    }

    @Test
    void testExistsByIdTrue() {
        when(userRepository.existsById(1)).thenReturn(true);

        assertTrue(userService.existsById(1));
    }

    @Test
    void testExistsByIdFalse() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertFalse(userService.existsById(1));
    }
}
