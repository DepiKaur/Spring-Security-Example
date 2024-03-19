package com.example.springsecurityexample.service;

import com.example.springsecurityexample.dto.UserDto;
import com.example.springsecurityexample.entity.User;

import java.util.List;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-03-19
 */
public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
