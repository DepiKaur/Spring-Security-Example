package com.example.springsecurityexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-03-19
 */

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login-form";
    }

    // add mapping for /access-denied

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
