package com.api.adega.api.service;

import com.api.adega.api.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> listAllUsers();

    Optional<User> findById(Long id);

    User createUser(User user);

    User updateUser(Long id, User userUpdated);

    void deleteUser(Long id);

    String getUserTypeByCredentials(String username, String password);
}
