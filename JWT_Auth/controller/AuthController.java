package com.abhi.BasicAuth.controller;

import com.abhi.BasicAuth.entity.LoginDTO;
import com.abhi.BasicAuth.entity.RegisterDTO;
import com.abhi.BasicAuth.services.Implementation.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello! This is Abhishek Kumar here. This is a demo api.";
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody RegisterDTO registerDTO){
        return userService.register(registerDTO);
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }
}
