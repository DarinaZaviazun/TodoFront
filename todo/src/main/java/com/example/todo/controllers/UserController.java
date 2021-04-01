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

    @PostMapping("/auth")
    public void login(@RequestBody Object o){
        System.out.println(o);
        System.out.println("OOOOOOO");
      // user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
