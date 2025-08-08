package com.example.testapp.controller;

import org.springframework.ui.Model; // pass data from the controller to the view
import com.example.testapp.model.LoginRequest;
import com.example.testapp.repository.userRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.testapp.model.UserModel;

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

    @PostMapping("/add")
    public String addUser(@ModelAttribute UserModel user, Model model) {
        repo.save(user);
        model.addAttribute("users", repo.findAll());
        return "home";
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "addUser";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute UserModel user, Model model) {
        user.setId(id);
        repo.save(user);
        model.addAttribute("users", repo.findAll());
        return "home";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        var user = repo.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "editUser";
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        model.addAttribute("users", repo.findAll());
        return "home";
    }
}
