package com.zeta.miniproject2.Restaurant.Management.System.Service;

import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Integer id);

    User updateUser(Integer id, User updatedUser);

    User patchUser(Integer id, User updatedUser);

    boolean deleteUser(Integer id);

    boolean existsById(Integer id);
}
