package com.ouronet.ouronet.usecase;

import com.ouronet.ouronet.models.User;
import com.ouronet.ouronet.repositories.UserRepository;
import com.ouronet.ouronet.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserControllerUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User registerUser(User user) {
        if (userRepository.findByName(user.getName()) != null) {
            throw new RuntimeException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getHashedPassword()));
        userRepository.save(user);
        return user;
    }

    public String authenticate(String username, String password) {
        User user = userRepository.findByName(username);
        if (passwordEncoder.matches(password, user.getHashedPassword())) {
            return JwtService.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(UUID id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        if (userDetails.getHashedPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getHashedPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
