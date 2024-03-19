package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.dto.UserDto;
import com.example.springsecurityexample.entity.User;
import com.example.springsecurityexample.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-03-19
 */

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showLandingPage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("userDto") UserDto userDto,
                               BindingResult bindingResult,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            bindingResult.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/usersList")
    public String usersList(Model model) {
        List<UserDto> usersList = userService.findAllUsers();
        model.addAttribute("usersList", usersList);
        return "users-list";
    }

    @GetMapping("/customers")
    public String showHomePage() {
        return "home-page";
    }

    // add mapping for /admin

    @GetMapping("/admin")
    public String showAdmin() {
        return "admin";
    }
}
