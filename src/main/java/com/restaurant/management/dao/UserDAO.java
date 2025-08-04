package com.restaurant.management.dao;

import com.restaurant.management.model.User;

import java.util.List;

public interface UserDAO {

    User getUserById(int userId);
    List<User> getUsersByRole(String role);

}
