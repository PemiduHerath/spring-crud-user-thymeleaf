package com.example.testapp.model;

import jakarta.persistence.*;

import lombok.*;

//this model class act as a table in the database

@Entity // specify that this class is Database Entity(table)
@Data //generate getters, setters, and other methods
@NoArgsConstructor // need for JPA
@Table(name = "login") // specify the table name in the database that use
public class UserModel {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlogin")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "firstname", nullable = false)
    private String firstname;
    @Column(name = "lastname", nullable = false)
    private String lastname;
    @Column(name = "email")
    private String email;

}

