package com.example.mesapp.controllers;

import com.example.mesapp.entities.User;
import com.example.mesapp.repository.LoginRequest;
import com.example.mesapp.repository.UserRepository;
import com.example.mesapp.responses.DefaultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private final UserRepository repository;

    public MainController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public DefaultResponse<Object> checkDb() {
        DefaultResponse<Object> dr = new DefaultResponse<Object>();
        dr.setError(false);
        dr.setMessage("Db Connected");
        return dr;
    }

    @PostMapping("/login")
    public DefaultResponse<User> login(@RequestBody LoginRequest request) {
        DefaultResponse<User> dr = new DefaultResponse<>();
        List<User> users = repository.findByUsername(request.username);
        if (users.isEmpty()) {
            dr.setError(true);
            dr.setMessage("User not found");
            dr.setData(null);
            return dr;
        }
        for (User u : users) {
            if (u.getPassword().equals(request.password)) {
                dr.setError(false);
                dr.setMessage("Login Successful");
                dr.setData(u);
                return dr;
            }
        }
        dr.setError(true);
        dr.setMessage("Invalid Password");
        dr.setData(null);
        return dr;
    }
}
