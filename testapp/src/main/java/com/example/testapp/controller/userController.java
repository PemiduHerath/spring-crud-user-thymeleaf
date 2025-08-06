package com.example.testapp.controller;

import org.springframework.ui.Model; // pass data from the controller to the view
import com.example.testapp.model.LoginRequest;
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
    public String login(@ModelAttribute LoginRequest loginRequest, Model model) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        var user = repo.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return "login";
        } else {
            model.addAttribute("users", repo.findAll());
            return "home";
        }
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/home";
    }

}
