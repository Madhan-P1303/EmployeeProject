package com.example.employee.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.employee.entity.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}