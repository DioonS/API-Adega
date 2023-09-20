package com.api.adega.api.service.impl;

import com.api.adega.api.entities.User;
import com.api.adega.api.repository.UserRepo;
import com.api.adega.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public List<User> listAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User updateUser(Long id, User userUpdated) {
        Optional<User> userExists = userRepo.findById(id);
        if (userExists.isPresent()) {
            User user = userExists.get();
            user.setName(userUpdated.getName());
            user.setUsername(userUpdated.getUsername());
            return userRepo.save(user);
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public String getUserTypeByCredentials(String username, String password) {

        User user = userRepo.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user.getUserType();
        } else {
            return null;
        }
    }
}
