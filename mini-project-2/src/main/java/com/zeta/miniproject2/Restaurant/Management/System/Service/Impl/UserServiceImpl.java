package com.zeta.miniproject2.Restaurant.Management.System.Service.Impl;

import com.zeta.miniproject2.Restaurant.Management.System.Exception.ResourceNotFoundException;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.User;
import com.zeta.miniproject2.Restaurant.Management.System.Repository.UserRepository;
import com.zeta.miniproject2.Restaurant.Management.System.Service.UserService;
import com.zeta.miniproject2.Restaurant.Management.System.Util.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        log.info("Creating new user: {}", user);
        User savedUser = userRepository.save(user);
        log.info("User created with ID: {}", savedUser.getUserId());
        return savedUser;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.warn("No users found");
        } else {
            log.info("Total users found: {}", users.size());
        }
        return users;
    }

    @Override
    public User getUserById(Integer id) {
        log.info("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found", id);
                    return new ResourceNotFoundException("User not found with ID: " + id);
                });
    }

    @Override
    public User updateUser(Integer id, User updatedUser) {
        log.info("Fully updating user with ID: {}",id);
        User existingUser = getUserById(id);

        BeanUtils.copyProperties(updatedUser, existingUser, "userId");

        User savedUser = userRepository.save(existingUser);
        log.info("User with ID {} successfully updated", id);
        return savedUser;

    }

    @Override
    public User patchUser(Integer id, User updatedUser) {
        log.info("Updating user with ID: {}", id);
        User existingUser = getUserById(id);

        existingUser = EntityUtil.copyNonNullProperties(updatedUser, existingUser);

        User savedUser = userRepository.save(existingUser);
        log.info("User with ID {} successfully updated", id);
        return savedUser;
    }

    @Override
    public boolean deleteUser(Integer id) {
        log.info("Deleting user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            log.warn("User with ID {} does not exist", id);
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        log.info("User with ID {} successfully deleted", id);
        return true;
    }

    @Override
    public boolean existsById(Integer id) {
        log.info("Checking existence of user with ID: {}", id);
        boolean exists = userRepository.existsById(id);
        if (exists) {
            log.info("User with ID {} exists", id);
        } else {
            log.warn("User with ID {} does not exist", id);
        }
        return exists;
    }
}
