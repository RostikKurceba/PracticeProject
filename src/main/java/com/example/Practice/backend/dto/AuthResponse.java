package com.example.Practice.backend.dto;

public class AuthResponse {

    public Long id;

    public String token;
    public String role;

    public String lastName;
    public String firstName;
    public String middleName;

    public AuthResponse(Long id,
                        String token,
                        String role,
                        String lastName,
                        String firstName,
                        String middleName) {

        this.id = id;
        this.token = token;
        this.role = role;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }
}