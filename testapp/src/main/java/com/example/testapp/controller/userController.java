package com.example.testapp.controller;

import org.springframework.ui.Model; // pass data from the controller to the view(bridege between controller and view to send data)
import com.example.testapp.model.LoginRequest;
import com.example.testapp.repository.userRepo;
import com.example.testapp.service.UserLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.testapp.model.UserModel;

// @CrossOrigin(origins = " ") this use when we implement frontend and backend separately with different ports. in here i use thymeleaf so no need to use this

// @RestController is used for RESTful web services, it returns JSON or XML responses only
@Controller // use this bcz it sends html pages to the browser(views)
public class userController {

    @Autowired // this is used to inject the userRepo bean into this controller(create object automatically)
    private userRepo repo;

    @Autowired
    private UserLoginService loginService;
 
    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/api/login")
    public String login(@ModelAttribute LoginRequest loginRequest, Model model) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        boolean user = loginService.checkLogin(username, password);
        if (user == false) {
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
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists!");
            model.addAttribute("user", user);
            return "addUser";
        } else {
            loginService.addUser(user.getUsername(), user.getPassword());
            model.addAttribute("users", repo.findAll());
            return "home";
        }
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "addUser";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute UserModel user, Model model) {
        loginService.updateUserPassword(user.getUsername(), user.getPassword());
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

//pathvariable is used to get the value from the URL
//modelattribute is used to set the form data to the model class
