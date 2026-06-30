package com.example.Practice.backend.dto;

public class TeacherDTO {

    public Long id;
    public String lastName;
    public String initials;
    public String subject;

    public TeacherDTO(Long id,
                      String lastName,
                      String initials,
                      String subject) {

        this.id = id;
        this.lastName = lastName;
        this.initials = initials;
        this.subject = subject;
    }

}