package com.example.passkey_authentication.service;

import com.example.passkey_authentication.dto.UserDTO;
import com.example.passkey_authentication.model.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);
    User getUserById(Long id);
    List<User> getAllUsers();
}
