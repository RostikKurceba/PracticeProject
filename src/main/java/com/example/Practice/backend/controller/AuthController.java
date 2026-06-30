package com.example.Practice.backend.controller;

import com.example.Practice.backend.dto.LoginRequest;
import com.example.Practice.backend.dto.RegisterRequest;
import com.example.Practice.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register/{role}")
    public Object register(
            @PathVariable String role,
            @RequestBody RegisterRequest req
    ) {
        return service.register(role, req);
    }

    @PostMapping("/login/{role}")
    public Object login(
            @PathVariable String role,
            @RequestBody LoginRequest req
    ) {
        return service.login(role, req);
    }



}