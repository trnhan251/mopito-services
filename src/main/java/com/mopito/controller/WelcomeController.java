package com.mopito.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public String welcome() {
        return "Welcome to Mopito API. Server is running on PORT 8080";
    }

    @GetMapping("/welcome")
    public String welcomeSecured() {
        return "Testing security";
    }

    @GetMapping("/user")
    public String user() {
        return "User";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }
}
