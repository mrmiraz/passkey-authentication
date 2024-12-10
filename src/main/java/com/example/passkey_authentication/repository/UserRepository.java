package com.example.passkey_authentication.repository;

import com.example.passkey_authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
