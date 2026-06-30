package com.example.Practice.backend.controller;

import com.example.Practice.backend.dto.AuthResponse;
import com.example.Practice.backend.dto.LoginRequest;
import com.example.Practice.backend.dto.RegisterRequest;
import com.example.Practice.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login/{role}")
    public AuthResponse login(@RequestBody LoginRequest req,
                              @PathVariable String role) {
        return authService.login(req, role);
    }
}