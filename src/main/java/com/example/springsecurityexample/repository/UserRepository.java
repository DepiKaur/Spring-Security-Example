package com.example.springsecurityexample.repository;

import com.example.springsecurityexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-03-19
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String email);
}
