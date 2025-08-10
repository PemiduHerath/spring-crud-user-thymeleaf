package com.example.testapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.testapp.model.UserModel;
import com.example.testapp.repository.userRepo;

@Service // this is used to mark this class as a service component in the Spring context
public class UserLoginService {

    @Autowired // this is used to inject the userRepo bean into this service(create object automatically)
    private userRepo repo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean checkLogin(String username, String password) {
        Optional<UserModel> user = repo.findByUsername(username);
        if (user.isPresent()) {
            return passwordEncoder.matches(password, user.get().getPassword());
        }
        return false;
    }

    public void addUser(String username, String password, String firstname, String lastname, String email) {
        UserModel user = new UserModel();
        String encodedPassword = passwordEncoder.encode(password);
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        repo.save(user);
    }

    public void updateUserPassword(String username, String newPassword, String firstname, String lastname, String email) {
        Optional<UserModel> userOptional = repo.findByUsername(username);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            repo.save(user);
        }
    }
}
