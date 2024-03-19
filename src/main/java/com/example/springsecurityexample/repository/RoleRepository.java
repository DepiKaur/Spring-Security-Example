package com.example.springsecurityexample.repository;

import com.example.springsecurityexample.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-03-19
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
