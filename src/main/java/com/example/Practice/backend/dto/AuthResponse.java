package com.example.Practice.backend.dto;

public class AuthResponse {
    public String token;
    public String role;
    public String lastName;
    public String firstName;
    public String middleName;

    public AuthResponse(String token, String role,
                        String lastName,
                        String firstName,
                        String middleName) {
        this.token = token;
        this.role = role;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }
}