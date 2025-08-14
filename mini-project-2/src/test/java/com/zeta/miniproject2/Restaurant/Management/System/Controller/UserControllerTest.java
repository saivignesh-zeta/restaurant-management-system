package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.UserDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.User;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Enums.UserRole;
import com.zeta.miniproject2.Restaurant.Management.System.Service.UserService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import com.zeta.miniproject2.Restaurant.Management.System.Util.UserMapper;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .userId(1)
                .name("John Doe")
                .role(UserRole.ADMIN)
                .build();

        userDTO = new UserDTO(1, "John Doe", UserRole.ADMIN);
    }

    @Test
    void testCreateUser() {
        try (MockedStatic<UserMapper> mockedMapper = mockStatic(UserMapper.class)) {
            when(userService.createUser(any(User.class))).thenReturn(user);
            mockedMapper.when(() -> UserMapper.toDTO(user)).thenReturn(userDTO);

            ResponseEntity<UserDTO> response = controller.createUser(user);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(userDTO, response.getBody());
            verify(userService, times(1)).createUser(user);
        }
    }

    @Test
    void testGetAllUsers() {
        try (MockedStatic<UserMapper> mockedMapper = mockStatic(UserMapper.class)) {
            when(userService.getAllUsers()).thenReturn(List.of(user));
            mockedMapper.when(() -> UserMapper.toDTO(user)).thenReturn(userDTO);

            ResponseEntity<List<UserDTO>> response = controller.getAllUsers();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().size());
            assertEquals(userDTO, response.getBody().get(0));
            verify(userService, times(1)).getAllUsers();
        }
    }

    @Test
    void testGetUserById() {
        try (MockedStatic<UserMapper> mockedMapper = mockStatic(UserMapper.class)) {
            when(userService.getUserById(1)).thenReturn(user);
            mockedMapper.when(() -> UserMapper.toDTO(user)).thenReturn(userDTO);

            ResponseEntity<UserDTO> response = controller.getUserById(1);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(userDTO, response.getBody());
            verify(userService, times(1)).getUserById(1);
        }
    }

    @Test
    void testUpdateUser() {
        try (MockedStatic<UserMapper> mockedMapper = mockStatic(UserMapper.class)) {
            when(userService.updateUser(eq(1), any(User.class))).thenReturn(user);
            mockedMapper.when(() -> UserMapper.toDTO(user)).thenReturn(userDTO);

            ResponseEntity<UserDTO> response = controller.updateUser(1, user);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(userDTO, response.getBody());
            verify(userService, times(1)).updateUser(1, user);
        }
    }

    @Test
    void testPatchUser() {
        try (
                MockedStatic<UserMapper> mockedMapper = mockStatic(UserMapper.class);
                MockedStatic<EntityUtil> mockedEntityUtil = mockStatic(EntityUtil.class)
        ) {
            when(userService.getUserById(1)).thenReturn(user);
            mockedEntityUtil.when(() -> EntityUtil.copyNonNullProperties(any(User.class), any(User.class)))
                    .thenReturn(user);
            when(userService.patchUser(eq(1), any(User.class))).thenReturn(user);
            mockedMapper.when(() -> UserMapper.toDTO(user)).thenReturn(userDTO);

            ResponseEntity<UserDTO> response = controller.patchUser(1, user);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(userDTO, response.getBody());
            verify(userService, times(1)).getUserById(1);
            verify(userService, times(1)).patchUser(1, user);
        }
    }

    @Test
    void testDeleteUser() {
        when(userService.deleteUser(1)).thenReturn(true);

        ResponseEntity<Void> response = controller.deleteUser(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1);
    }
}
