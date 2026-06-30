package com.example.Practice.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String lastName;
    public String firstName;
    public String middleName;

    @Column(unique = true)
    public String email;

    public String password;

    public String subject;
}