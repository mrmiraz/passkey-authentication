package com.example.passkey_authentication.service.impl;

import com.example.passkey_authentication.dto.UserDTO;
import com.example.passkey_authentication.exception.ResourceNotFoundException;
import com.example.passkey_authentication.model.User;
import com.example.passkey_authentication.repository.UserRepository;
import com.example.passkey_authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}