package com.example.todo.controllers;

import com.example.todo.dao.UserDAO;
import com.example.todo.models.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/save")
    public void save(@RequestBody User user){
        System.out.println("TUT1");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @PostMapping("/auth")
    public void login(){
        System.out.println("TUT2");
    }
}
