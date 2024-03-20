package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.dto.UserDto;
import com.example.springsecurityexample.entity.User;
import com.example.springsecurityexample.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

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
        // form validation
        if (bindingResult.hasErrors()) {
            return "/register";
        }

        //check the database if user already exists

        Optional<User> savedUser = userService.findUserByEmail(userDto.getEmail());

        if(savedUser.isPresent()){
            model.addAttribute("webUser", new UserDto());
            model.addAttribute("registrationError", "Email already exists!");
            return "/register";
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("userDto", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // add an InitBinder ... to convert trim input strings
    // remove leading and trailing whitespace
    // resolve issue for our validation
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
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
