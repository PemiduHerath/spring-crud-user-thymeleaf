package com.example.testapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.testapp.model.UserModel;


@Repository // every repository interface have this annotation
public interface userRepo extends JpaRepository<UserModel, Long> { //UserModel is the entity class(model), Long is the type of the primary key(pk is idlogin)
    // JPA will automatically create all the SQL queries for basic CRUD operations using method names(without queries)
    
    // Find user by username and password
    Optional<UserModel> findByUsernameAndPassword(String username, String password);

     // Additional methods can be defined here if we needed..
}
