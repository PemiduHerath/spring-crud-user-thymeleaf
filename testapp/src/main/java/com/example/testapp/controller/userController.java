package com.example.testapp.controller;

import com.example.testapp.repository.userRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin(origins = " ") this use when we implement frontend and backend separately with different ports. in here i use thymeleaf so no need to use this

@Controller
public class userController {

    @Autowired
    private userRepo repo;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

     @PostMapping("/api/login")
    public String login(@ModelAttribute LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        var user = repo.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return "login";
        } else {
            return "home";
        }
    }
    // DTO class for login request
    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
